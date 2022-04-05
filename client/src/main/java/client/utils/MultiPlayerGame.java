package client.utils;

import client.scenes.MainCtrl;
import commons.messages.*;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import javafx.application.Platform;
import org.glassfish.jersey.client.ClientConfig;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

public class MultiPlayerGame {
    private static final String SERVER = "http://localhost:8080/";
    private final static String API_PATH = "/api/games/multiplayer";
    private final MainCtrl mainCtrl;

    private String playerId;
    private String id;

    private boolean gameEnded;

    /**
     * Constructor for MultiPlayerGame, creating a new multi-player game.
     @param mainCtrl - The main controller used for accessing the scenes.
     */
    public MultiPlayerGame(MainCtrl mainCtrl) {
        this.gameEnded = false;
        var m = newGame();
        this.id = m.getId();
        this.playerId = m.getPlayerId();
        this.mainCtrl = mainCtrl;
        this.subscribeToMessages();
    }

    /**
     * newGame registers the current game with the server and sets the game id
     * @return the id which the server assigned to this game
     */
    private NewGameMessage newGame() {
        var m = ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path(API_PATH).path("new") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(NewGameMessage.class);
        return m;
    }

    /**
     * giveAnswer submits an answer to the server
     * @param answer the numeric value of the answer, what it means depends on what type of question is being answered
     */
    public void giveAnswer(int answer) {
        var a = new AnswerMessage(answer);
        ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path(API_PATH) //
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
                var message = ClientBuilder.newClient(new ClientConfig())
                        .target(SERVER).path(API_PATH)
                        .path(this.id).path(this.playerId)
                        .request(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .get(Message.class);
                this.handleMessage(message);
            }
        }).start();
    }

    /**
     * handleMessage handles a message sent by the server to the client
     * @param m the message that was sent
     */
    private void handleMessage(Message m) {
        System.out.println(m.getClass().toString());

        if (m instanceof NextQuestionMessage) {
            Platform.runLater(() -> {
                mainCtrl.setQuestionMultiPlayer(((NextQuestionMessage) m).getQuestion());
            });
        } else if (m instanceof GameEndedMessage) {
            gameEnded = true;
            Platform.runLater(() -> {
                mainCtrl.gameEnded();
            });
        }
    }
}
