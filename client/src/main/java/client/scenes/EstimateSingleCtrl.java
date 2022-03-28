package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.questions.Estimate;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Timer;
import java.util.TimerTask;

public class EstimateSingleCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    private Estimate question;

    @FXML
    private TextField answer;
    @FXML
    private Button confirm;
    @FXML
    private Label questionText;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private DialogPane dialogPane;
    @FXML
    private Button YesExit;
    @FXML
    private Button NoExit;

    @Inject
    public EstimateSingleCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    public void answerField() {
        disableAll();
        mainCtrl.getGame().giveAnswer(Integer.parseInt(answer.getText())); //be careful when receiving the answer in the server
    }

    public void setQuestion(Estimate question) {
        this.question = question;
        this.questionText.setText("How much energy does: " + question.getActivity().getTitle() + " consume?");

        //TODO: Set images
    }

    public void goBackMainMenu() {
        mainCtrl.showMainMenu();
    }

    /**
     * Starting 2 timers corresponding to the progress bar and disabling the buttons after a specific period of time
     */
    public void startTimer() {
        Timer gameTimer = new Timer();
        Timer progressBarTimer = new Timer();
        /**
         * Task for disabling the buttons and not letting the progress bar go under 0
         */
        TimerTask timeOut = new TimerTask() {
            @Override
            public void run() {
                disableAll();
                progressBarTimer.cancel();
            }
        };

        /**
         * Task for decreasing the progress bar with a specific amount every 40ms
         */
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

    private void disableAll() {
        answer.setDisable(true);
        confirm.setDisable(true);
    }

    public void showDialogExit() {
        dialogPane.setVisible(true);
    }

    public void hideDialogExit() {
        dialogPane.setVisible(false);
    }

}
