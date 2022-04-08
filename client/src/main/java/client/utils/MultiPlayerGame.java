package client.utils;

import client.scenes.MainCtrl;
import commons.Player;
import commons.exceptions.NameAlreadyPickedException;
import commons.messages.*;
import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import javafx.application.Platform;
import org.glassfish.jersey.client.ClientConfig;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

public class MultiPlayerGame {
    private final static String API_PATH = "/api/games/multiplayer";

    private final MainCtrl mainCtrl;

    private final String playerId;

    private final String name;

    private final String id;

    private final Config config;

    private boolean gameEnded;

    private final Player p;

    /**
     * Constructor for MultiPlayerGame, creating a new multi-player game.
     * @param config - The config of the server location.
     * @param mainCtrl - The main controller used for accessing the scenes.
     * @param name - The name of the new player.
     */
    public MultiPlayerGame(Config config, MainCtrl mainCtrl, String name) throws NameAlreadyPickedException {
        this.config = config;
        this.gameEnded = false;
        this.name = name;
        var m = newGame();
        this.id = m.getId();
        this.playerId = m.getPlayerId();
        this.mainCtrl = mainCtrl;
        this.p = new Player(id, name, true);
        p.setScore(0);
        this.subscribeToMessages();
    }

    public void setScore(Integer n) {
        p.setScore(p.getScore() + n);
    }

    public Player getPlayer() {
        return this.p;
    }
    /**
     * newGame registers the current game with the server and sets the game id
     *
     * @return the id which the server assigned to this game
     */

    private NewGameMessage newGame() throws NameAlreadyPickedException {
        var m = ClientBuilder.newClient(new ClientConfig()) //
            .target(config.getServerLocation()).path(API_PATH).path("new") //
            .request(APPLICATION_JSON) //
            .accept(APPLICATION_JSON) //
            .post(Entity.entity(new SendNameMessage(this.name), APPLICATION_JSON), Message.class);
        if (m instanceof NewGameMessage) {
            return (NewGameMessage) m;
        } else if (m instanceof NameAlreadyPickedMessage) {
            var msg = (NameAlreadyPickedMessage) m;
            throw new NameAlreadyPickedException(msg.getName(), msg.getPickedNames());
        } else {
            throw new RuntimeException("illegal message type in new game: " + m.getClass());
        }
    }

    /**
     * giveAnswer submits an answer to the server.
     *
     * @param answer the numeric value of the answer, what it means depends on what type of question is being answered
     */
    public void giveAnswer(int answer) {
        var a = new AnswerMessage(answer);
        ClientBuilder.newClient(new ClientConfig()) //
            .target(config.getServerLocation()).path(API_PATH) //
            .path(this.id).path(this.playerId) //
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
                try {
                    var message = ClientBuilder.newBuilder()
                        .readTimeout(15, TimeUnit.SECONDS)
                        .newClient(new ClientConfig())
                        .target(config.getServerLocation()).path(API_PATH)
                        .path(this.id).path(this.playerId)
                        .request(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .get(Message.class);
                    this.handleMessage(message);
                } catch (ProcessingException e) {
                    if (e.getCause() instanceof TimeoutException) {
                        System.out.println("timed out waiting for next message");
                        continue;
                    } else {
                        throw e;
                    }
                }
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
            Platform.runLater(() -> {
                mainCtrl.setQuestionMultiPlayer(((NextQuestionMessage) m).getQuestion());
                mainCtrl.startMultiPlayerTimer();
            });
        } else if (m instanceof GameEndedMessage) {
            gameEnded = true;
            Platform.runLater(() -> {
                mainCtrl.gameEnded();
            });
        } else if (m instanceof GameStartingMessage) {
            Platform.runLater(() -> {
                mainCtrl.showStartingScreen();
            });
        } else if (m instanceof UpdatePlayersMessage) {
            Platform.runLater(() -> {
                mainCtrl.setPlayerList(((UpdatePlayersMessage) m).getPlayers());
            });
        }
    }

    /**
     * startGame attempts to start the multiplayer game.
     */
    public void startGame() {
        ClientBuilder.newClient(new ClientConfig()) //
            .target(config.getServerLocation()).path(API_PATH) //
            .path(this.id).path("start") //
            .request(APPLICATION_JSON) //
            .accept(APPLICATION_JSON) //
            .get();
    }
}
