package client.utils;

import client.scenes.MainCtrl;
import commons.Player;
import commons.messages.*;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import javafx.application.Platform;
import org.glassfish.jersey.client.ClientConfig;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

public class SinglePlayerGame {
    private final static String API_PATH = "/api/games/singleplayer";

    private final MainCtrl mainCtrl;

    private final String id;

    private final Config config;

    private String name;

    private Integer score;

    private final Player p;

    private boolean gameEnded;

    /**
     * Constructor for SinglePlayerGame, creating a new single player game.
     *
     * @param config   - The config to use for the game.
     * @param mainCtrl - The main controller used for accessing the scenes.
     * @param name     - The name of the player that will play the game.
     */
    public SinglePlayerGame(Config config, MainCtrl mainCtrl, String name) {
        this.config = config;
        this.gameEnded = false;
        this.id = newGame(name);
        this.p = new Player(id, name, true);
        this.mainCtrl = mainCtrl;
        p.setScore(0);
        this.subscribeToMessages();
        mainCtrl.showStartingScreen();
    }

    /**
     * newGame registers the currthent game wi the server and sets the game id
     *
     * @return the id which the server assigned to this game
     */
    private String newGame(String name) {
        var m = ClientBuilder.newClient(new ClientConfig()) //
            .target(config.getServerLocation()).path(API_PATH).path("new") //
            .request(APPLICATION_JSON) //
            .accept(APPLICATION_JSON) //
            .post(Entity.entity(new SendNameMessage(name), APPLICATION_JSON), NewGameMessage.class);
        return m.getId();
    }

    /**
     * giveAnswer submits an answer to the server.
     *
     * @param answer the numeric value of the answer, what it means depends on what type of question is being answered
     */
    public void giveAnswer(int answer) {
        var a = new AnswerMessage(answer);
        ClientBuilder.newClient(new ClientConfig()) //
            .target(config.getServerLocation()).path(API_PATH).path(this.id) //
            .request(APPLICATION_JSON) //
            .accept(APPLICATION_JSON) //
            .post(Entity.entity(a, APPLICATION_JSON));
    }

    public void updScore(int score) {
        var a = new UpdateScoreMessage(score);
        ClientBuilder.newClient(new ClientConfig()) //
            .target(config.getServerLocation()).path(API_PATH).path(this.id) //
            .request(APPLICATION_JSON) //
            .accept(APPLICATION_JSON) //
            .post(Entity.entity(a, APPLICATION_JSON));
    }

    /**
     * subscribeToMessages will keep polling the server for new messages until the current game has ended
     * when a new message is received it will be handled
     */
    private void subscribeToMessages() {
        new Thread(() -> {
            while (!gameEnded) {
                var message = ClientBuilder.newClient(new ClientConfig())
                    .target(config.getServerLocation()).path(API_PATH).path(this.id)
                    .request(APPLICATION_JSON)
                    .accept(APPLICATION_JSON)
                    .get(Message.class);
                this.handleMessage(message);
            }
        }).start();
    }

    /**
     * handleMessage handles a message sent by the server to the client
     *
     * @param m the message that was sent
     */
    private void handleMessage(Message m) {
        System.out.println(m.getClass().toString());

        if (m instanceof NextQuestionMessage) {
            //TODO(Friso): provide the question to the question controller
            Platform.runLater(() -> {
                mainCtrl.setQuestionSinglePlayer(((NextQuestionMessage) m).getQuestion());
                mainCtrl.startSinglePlayerTimer();
            });
        } else if (m instanceof GameEndedMessage) {
            Platform.runLater(() -> {
                mainCtrl.gameEnded();
            });
        } else if (m instanceof SingleLeaderboardMessage) {
            Platform.runLater(() -> {
                mainCtrl.showLeaderboard(((SingleLeaderboardMessage) m).getLeaderBoard());
            });
        }
    }

    public Integer getPlayerScore() {
        return p.getScore();
    }

    public Player getPlayer() {
        return this.p;
    }

    public void setScore(Integer n) {
        p.setScore(p.getScore() + n);
    }

    public void endGame() {
        gameEnded = true;
        var a = new GameEndedMessage();
        ClientBuilder.newClient(new ClientConfig()) //
            .target(config.getServerLocation()).path(API_PATH).path(this.id) //
            .request(APPLICATION_JSON) //
            .accept(APPLICATION_JSON) //
            .post(Entity.entity(a, APPLICATION_JSON));
    }
}