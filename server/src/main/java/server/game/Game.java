package server.game;

import commons.Player;
import commons.game.HighScore;
import commons.messages.*;
import commons.questions.Question;
import server.database.GameRepository;
import server.database.PlayerRepository;
import server.database.ScoreRepository;
import server.datastructures.MultiMessageQueue;
import server.services.TimerService;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Game {
    private enum State {
        STARTING,
        QUESTION_PERIOD,
        GAME_ENDED,
    }

    private List<Question> questions;

    private final UUID id;

    private State state;

    private int currentQuestion;

    private final HashMap<String, Player> players;

    private final MultiMessageQueue messageQueue;

    private final HashMap<String, Integer> answers;

    private ScoreRepository scoreRepository;

    private PlayerRepository playerRepository;

    private GameRepository gameRepository;

    private TimerService timerService;

    private Timer timer = new Timer();

    private TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            advanceState();
        }
    };

    public Game(UUID id, List<Question> questions, GameRepository gameRepository, PlayerRepository playerRepository, ScoreRepository scoreRepository, TimerService timerService) {
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
        this.timerService = timerService;
        advanceState();
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
                timerService.runAfter(5, () -> {
                    this.state = State.QUESTION_PERIOD;
                    this.nextQuestion();
                });
                break;
            case QUESTION_PERIOD:
                /*
                 * waiting for the correct answer to be displayed
                 */
                timerService.runAfter(3, () -> {
                    if (this.currentQuestion >= this.questions.size()) {
                        this.state = State.GAME_ENDED;
                        this.persistScores();
                        this.setLeaderboard();
                        this.messageQueue.addMessage(new GameEndedMessage());
                        return;
                    }
                    this.nextQuestion();
                });
                break;
        }
    }

    private void persistScores() {
        var maybeGame = gameRepository.findById(this.id.toString());

        if (!maybeGame.isPresent()) {
            throw new IllegalStateException("game not present in repository");
        }

        for (Player p : players.values()) {
            var score = new HighScore(p.getScore(), p.getId(), maybeGame.get().getId());
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
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                advanceState();
            }
        };
        timer.schedule(timerTask, 10000);
    }

    private void setLeaderboard() {
        var players = this.scoreRepository.findAll()
            .stream()
            .map(score -> {
                System.out.println(score.getPlayerId());
                var player = this.playerRepository.findById(score.getPlayerId()).get();
                player.setScore(score.getScore());
                return player;
            })
            .collect(Collectors.toList());
        this.messageQueue.addMessage(new SingleLeaderboardMessage(players));
    }

    /**
     * providedAnswer sets the answer given by a certain player
     *
     * @param playerId the id of the player to set the answer of
     * @param answer   what the answer was
     */
    private void providedAnswer(String playerId, int answer) {
        this.answers.put(playerId, answer);
        this.updateScore(playerId, 250);
        timer.cancel();
        this.advanceState();
    }

    public void updateScore(String playerId, int score) {
        var p = this.players.get(playerId);
        p.setScore(p.getScore() + score);
        this.players.put(playerId, p);
    }

    /**
     * handleMessage handles messages sent from the client to the server.
     */
    public void handleMessage(String playerId, Message m) {
        if (m instanceof AnswerMessage) {
            var answer = (AnswerMessage) m;
            this.providedAnswer(playerId, answer.getAnswer());
        } else if (m instanceof UpdateScoreMessage) {
            var score = (UpdateScoreMessage) m;
            this.updateScore(playerId, score.getScore());
        }
    }

    public String getId() {
        return id.toString();
    }

    /**
     * addPlayer adds a player with a certain name to the game.
     *
     * @param name the name of the player to add.
     * @param id   the id of the player.
     * @return the newly created player.
     */
    public Player addPlayer(String name, String id, boolean singleplayer) {
        var p = new Player(id, name, singleplayer);
        p.setGameId(id);
        players.put(p.getId(), p);

        playerRepository.save(p);

        return p;
    }

    /**
     * addMessageConsumer adds a message consumer for a player.
     *
     * @param id the id of the player.
     * @param c  the consumer of the message.
     */
    public void addMessageConsumer(String id, Consumer<Message> c) {
        messageQueue.setConsumer(id, c);
    }

    /**
     * resetConsumer resets the message consumer for a certain player.
     *
     * @param id the id of the player.
     */
    public void resetConsumer(String id) {
        messageQueue.resetConsumer(id);
    }
}
