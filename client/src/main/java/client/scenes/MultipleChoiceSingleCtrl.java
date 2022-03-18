package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.util.Timer;
import java.util.TimerTask;

public class MultipleChoiceSingleCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    @FXML
    private Button answerA;
    @FXML
    private Button answerB;
    @FXML
    private Button answerC;

    @Inject
    public MultipleChoiceSingleCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    public void goBackMainMenu() {
        mainCtrl.showMainMenu();
    }

    public void disableAllButtons() {
        answerA.setDisable(true);
        answerB.setDisable(true);
        answerC.setDisable(true);
    }

    public void startTimer() {
        Timer gameTimer = new Timer();
        TimerTask timeOut = new TimerTask() {
            @Override
            public void run() {
                disableAllButtons();
            }
        };
        gameTimer.schedule(timeOut,2000);
    }
}
