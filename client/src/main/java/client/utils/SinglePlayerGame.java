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

    private boolean gameEnded;

    public SinglePlayerGame(MainCtrl mainCtrl) {
        this.gameEnded = false;
        this.id = newGame();
        this.mainCtrl = mainCtrl;
        this.subscribeToMessages();
    }

    private String newGame() {
        var m = ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path(API_PATH).path("new") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(NewGameMessage.class);
        return m.getId();
    }

    public void giveAnswer(int answer) {
        var a = new AnswerMessage(answer);
        ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path(API_PATH).path(this.id) //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(a, APPLICATION_JSON));
    }

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

    private void handleMessage(Message m) {
        System.out.println(m.getClass().toString());

        if (m instanceof NextQuestionMessage) {
            //TODO(Friso): provide the question to the question controller
            Platform.runLater(() -> {
                mainCtrl.setQuestionSinglePlayer(((NextQuestionMessage) m).getQuestion());
            });
        } else if (m instanceof GameEndedMessage) {
            gameEnded = true;
            Platform.runLater(() -> {
                mainCtrl.gameEnded();
            });
        }
    }
}
