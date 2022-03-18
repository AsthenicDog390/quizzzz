package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;

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
    @FXML
    private ProgressBar progressBar;

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
        Timer progressBarTimer = new Timer();
        TimerTask timeOut = new TimerTask() {
            @Override
            public void run() {
                disableAllButtons();
                progressBarTimer.cancel();
            }
        };

        TimerTask lowerBar = new TimerTask() {
            @Override
            public void run() {
                double progress = progressBar.getProgress();
                if(progress>0.004)
                progressBar.setProgress(progress-0.004);
            }
        };
        gameTimer.schedule(timeOut,10000);
        progressBarTimer.schedule(lowerBar,0,40);
    }

}
