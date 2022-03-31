package server.game;

import commons.Player;
import commons.game.HighScore;
import commons.messages.AnswerMessage;
import commons.messages.GameEndedMessage;
import commons.messages.Message;
import commons.messages.NextQuestionMessage;
import commons.questions.Question;
import server.database.GameRepository;
import server.database.PlayerRepository;
import server.database.ScoreRepository;
import server.datastructures.MultiMessageQueue;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public class Game {
    private enum State {
        STARTING,
        QUESTION_PERIOD,
        GAME_ENDED,
    }

    private List<Question> questions;
    private UUID id;
    private State state;
    private int currentQuestion;

    private HashMap<String, Player> players;
    private MultiMessageQueue messageQueue;
    private HashMap<String, Integer> answers;

    private ScoreRepository scoreRepository;
    private PlayerRepository playerRepository;
    private GameRepository gameRepository;

    public Game(UUID id, List<Question> questions, GameRepository gameRepository, PlayerRepository playerRepository, ScoreRepository scoreRepository) {
        if (questions == null) {
            throw new IllegalArgumentException("question list must not be null");
        } else if (questions.size() != 20) {
            throw new IllegalArgumentException("new SinglePlayerGame must receive exactly 20 questions");
        }
        for (int i = 0; i < 20; i++) {
            if (questions.get(i) == null) {
                throw new IllegalArgumentException("all questions must not be null");
            }
        }

        this.questions = questions;
        this.id = id;
        this.players = new HashMap<>();
        this.messageQueue = new MultiMessageQueue();
        this.answers = new HashMap<>();
        this.state = State.STARTING;
        this.gameRepository = gameRepository;
        this.playerRepository = playerRepository;
        this.scoreRepository = scoreRepository;
    }

    public void start() {
        this.currentQuestion = 0;
        this.state = State.STARTING;
        this.advanceState();
    }

    protected Game(UUID id) {
        this.id = id;
        this.players = new HashMap<>();
        this.messageQueue = new MultiMessageQueue();
        this.answers = new HashMap<>();
    }

    private void advanceState() {
        switch (this.state) {
            case STARTING:
                this.state = State.QUESTION_PERIOD;
                this.nextQuestion();
                break;
            case QUESTION_PERIOD:
                if (this.currentQuestion >= this.questions.size()) {
                    this.state = State.GAME_ENDED;
                    this.messageQueue.addMessage(new GameEndedMessage());
                    this.persistScores();
                    return;
                }
                this.nextQuestion();
                break;
        }
    }

    private void persistScores() {
        var maybeGame = gameRepository.findById(this.id.toString());

        if (!maybeGame.isPresent()) {
            throw new IllegalStateException("game not present in repository");
        }

        for (Player p: players.values()) {
            var score = new HighScore(p.getScore(), p, maybeGame.get());
            scoreRepository.save(score);
        }

        maybeGame = gameRepository.findById(this.id.toString());

        return;
    }

    /**
     * nextQuestion sends a message to show the next question to all players
     */
    private void nextQuestion() {
        this.messageQueue.addMessage(new NextQuestionMessage(this.questions.get(this.currentQuestion)));
        this.currentQuestion++;
        this.answers.clear();
    }

    /**
     * providedAnswer sets the answer given by a certain player
     * @param playerId the id of the player to set the answer of
     * @param answer what the answer was
     */
    private void providedAnswer(String playerId, int answer) {
        this.answers.put(playerId, answer);
        this.updateScore(playerId, 250);
        this.advanceState();
    }

    private void updateScore(String playerId, int score) {
        var p = this.players.get(playerId);
        p.setScore(p.getScore() + score);
        this.players.put(playerId, p);
    }

    /**
     * handleMessage handles messages sent from the client to the server.
     * */
    public void handleMessage(String playerId, Message m) {
        if (m instanceof AnswerMessage) {
            var answer = (AnswerMessage)m;
            this.providedAnswer(playerId, answer.getAnswer());
        }
    }

    public String getId() {
        return id.toString();
    }

    /**
     * addPlayer adds a player with a certain name to the game
     * @param name the name of the player to add
     * @return the newly created player
     */
    public Player addPlayer(String name) {
        var p = new Player(UUID.randomUUID().toString(), name);
        p.setGameId(id.toString());
        players.put(p.getId(), p);

        playerRepository.save(p);

        return p;
    }

    /**
     * addMessageConsumer adds a message consumer for a player
     * @param id the id of the player
     * @param c the consumer of the message
     */
    public void addMessageConsumer(String id, Consumer<Message> c) {
        messageQueue.setConsumer(id, c);
    }

    /**
     * resetConsumer resets the message consumer for a certain player
     * @param id the id of the player
     */
    public void resetConsumer(String id) {
        messageQueue.resetConsumer(id);
    }
}
