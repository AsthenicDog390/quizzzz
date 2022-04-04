package client.utils;

import client.scenes.MainCtrl;
import commons.messages.*;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import javafx.application.Platform;
import org.glassfish.jersey.client.ClientConfig;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

public class SinglePlayerGame {
    private static final String SERVER = "http://localhost:8080/";
    private final static String API_PATH = "/api/games/singleplayer";
    private final MainCtrl mainCtrl;

    private String id;
    private String name;

    private boolean gameEnded;

    public SinglePlayerGame(MainCtrl mainCtrl, String name) {
        this.gameEnded = false;
        this.name = name;
        this.id = newGame();
        this.mainCtrl = mainCtrl;
        this.subscribeToMessages();
        mainCtrl.showStartingScreen();
    }

    /**
     * newGame registers the current game with the server and sets the game id
     * @return the id which the server assigned to this game
     */
    private String newGame() {
        var m = ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path(API_PATH).path("new") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(new SendNameMessage(this.name), APPLICATION_JSON), NewGameMessage.class);
        return m.getId();
    }

    /**
     * giveAnswer submits an answer to the server
     * @param answer the numeric value of the answer, what it means depends on what type of question is being answered
     */
    public void giveAnswer(int answer) {
        var a = new AnswerMessage(answer);
        ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path(API_PATH).path(this.id) //
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
                        .target(SERVER).path(API_PATH).path(this.id)
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
            //TODO(Friso): provide the question to the question controller
            Platform.runLater(() -> {
                mainCtrl.setQuestionSinglePlayer(((NextQuestionMessage) m).getQuestion());
                mainCtrl.startSinglePlayerTimer();
            });
        } else if (m instanceof GameEndedMessage) {
            gameEnded = true;
            Platform.runLater(() -> {
                mainCtrl.gameEnded();
            });
        }
    }
}
