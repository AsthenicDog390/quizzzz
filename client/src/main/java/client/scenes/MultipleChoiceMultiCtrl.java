package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.questions.LessExpensive;
import commons.questions.MoreExpensive;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;

import java.util.Timer;
import java.util.TimerTask;

public class MultipleChoiceMultiCtrl {
    private final ServerUtils server;

    private final MainCtrl mainCtrl;

    private MoreExpensive question;

    private Timer gameTimer = new Timer();

    private Timer progressBarTimer = new Timer();

    private boolean isChatVisible=false;

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
    private Pane chatPane;

    @FXML
    private Button power1;

    @FXML
    private Button power2;

    @FXML
    private Button power3;

    @Inject
    public MultipleChoiceMultiCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    /**
     * Function used for selecting the most left answer.
     */
    public void answerA() {
        disableAllButtons();
        mainCtrl.getMultiPlayerGame().giveAnswer(0);
    }

    /**
     * Function used for selecting the middle answer.
     */
    public void answerB() {
        disableAllButtons();
        mainCtrl.getMultiPlayerGame().giveAnswer(1);
    }

    /**
     * Function used for selecting the most right answer.
     */
    public void answerC() {
        disableAllButtons();
        mainCtrl.getMultiPlayerGame().giveAnswer(2);
    }

    public void goBackMainMenu() {
        mainCtrl.showMainMenu();
    }

    public void setQuestion(MoreExpensive question) {
        this.question = question;
        enableAllButtons();
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

    /**
     * Disable all the buttons so the user won't have the option to press multiple answers.
     */
    public void disableAllButtons() {
        buttonA.setDisable(true);
        buttonB.setDisable(true);
        buttonC.setDisable(true);
    }

    /**
     * Disable all the buttons so the user will be able to answer.
     */
    public void enableAllButtons() {
        buttonA.setDisable(false);
        buttonB.setDisable(false);
        buttonC.setDisable(false);
    }

    /**
     * Starting 2 timers corresponding to the progress bar and disabling the buttons after a specific period of time.
     */
    public void startTimer() {
        gameTimer = new Timer();
        progressBarTimer = new Timer();
        progressBar.setProgress(1);
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
         * Task for decreasing the progress bar with a specific amount every 40ms.
         */
        TimerTask lowerBar = new TimerTask() {
            @Override
            public void run() {
                double progress = progressBar.getProgress();
                if (progress > 0.004) {
                    progressBar.setProgress(progress - 0.004);
                }
            }
        };
        gameTimer.schedule(timeOut, 10000);
        progressBarTimer.schedule(lowerBar, 0, 40);
        //timer is set on the server, this is only visual
    }

    /**
     * Function for showing or hiding the reaction chat.
     */
    public void showUnshowChat() {
        if(isChatVisible) {
            chatPane.setVisible(false);
            isChatVisible=false;
        }
        else {
            chatPane.setVisible(true);
            isChatVisible=true;
        }
    }

    /**
     * function for disabling and using the first power-up.
     */
    public void disablePower1(){
        power1.setDisable(true);
    }

    /**
     * function for disabling and using the second power-up.
     */
    public void disablePower2(){
        power2.setDisable(true);
    }

    /**
     * function for disabling and using the third power-up.
     */
    public void disablePower3(){
        power3.setDisable(true);
    }
}
