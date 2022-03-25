package client.scenes;

import client.utils.ServerUtils;
import commons.questions.LessExpensive;
import com.google.inject.Inject;
import commons.questions.MoreExpensive;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ProgressBar;

import java.util.Timer;
import java.util.TimerTask;

public class MultipleChoiceSingleCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    private MoreExpensive question;

    @FXML
    private Label score;
    @FXML
    private Button buttonA;
    @FXML
    private Button buttonB;
    @FXML
    private Button buttonC;
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
    public MultipleChoiceSingleCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }


    public void answerA() {
        disableAllButtons();
        giveAnswer(0);
    }

    public void answerB() {
        disableAllButtons();
        giveAnswer(1);
    }

    public void answerC() {
        disableAllButtons();
        giveAnswer(2);
    }

    public void giveAnswer(int answer) {
        mainCtrl.getGame().giveAnswer(answer);
    }


    public void setScore(int tscore){
        this.score.setText("Your Score:"+"\n"+tscore);
    }

    public void setQuestion(MoreExpensive question) {
        this.question = question;
        if (question instanceof LessExpensive) {
            this.questionText.setText("What activity takes less energy?");
        } else {
            this.questionText.setText("What activity takes more energy?");
        }

        this.buttonA.setText(question.getOptions()[0].getTitle());
        this.buttonB.setText(question.getOptions()[1].getTitle());
        this.buttonC.setText(question.getOptions()[2].getTitle());

        //TODO: Set images
    }

    public void goBackMainMenu() {
        mainCtrl.showMainMenu();
    }

    /**
     * Disable all the buttons so the user won't have the option to press multiple answers
     */
    public void disableAllButtons() {
        buttonA.setDisable(true);
        buttonB.setDisable(true);
        buttonC.setDisable(true);
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
                disableAllButtons();
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

    public void showDialogExit() {
        dialogPane.setVisible(true);
    }

    public void hideDialogExit() {
        dialogPane.setVisible(false);
    }

}
