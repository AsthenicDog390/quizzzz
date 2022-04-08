package client.scenes;

import client.utils.Config;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Activity;
import commons.questions.LessExpensive;
import commons.questions.MoreExpensive;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;

import java.util.Timer;
import java.util.TimerTask;

public class MultipleChoiceMultiCtrl {
    private final ServerUtils server;

    private final MainCtrl mainCtrl;

    private final Config config;

    private MoreExpensive question;

    private Integer n;

    private Timer gameTimer = new Timer();

    private Timer progressBarTimer = new Timer();

    private boolean isChatVisible=false;

    @FXML
    private Label score;

    @FXML
    private Label questionNumber;

    @FXML
    private Button buttonA;

    @FXML
    private Button buttonB;

    @FXML
    private Button buttonC;

    @FXML
    private ImageView imageA;

    @FXML
    private ImageView imageB;

    @FXML
    private ImageView imageC;

    @FXML
    private Label questionText;

    private Activity correctAnswer;

    private static final String API_PATH = "/images/";

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
    public MultipleChoiceMultiCtrl( Config config, ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.config = config;
        this.n = 1;
    }

    /**
     * Function used for selecting the most left answer.
     */
    public void answerA() {
        disableAllButtons();
        colorAnswers(0);
        giveAnswer(0);
    }

    /**
     * Function used for selecting the middle answer.
     */
    public void answerB() {
        disableAllButtons();
        colorAnswers(1);
        giveAnswer(1);
    }

    /**
     * Function used for selecting the most right answer.
     */
    public void answerC() {
        disableAllButtons();
        colorAnswers(2);
        giveAnswer(2);
    }

    /**
     * Removes the background colors from the buttons.
     */
    public void removeStyle() {
        buttonA.setStyle(null);
        buttonB.setStyle(null);
        buttonC.setStyle(null);
    }

    public void giveAnswer(int answer) {
        mainCtrl.getMultiPlayerGame().giveAnswer(answer);
        if (correctAnswer.getActivity_ID() == question.getOptions()[answer].getActivity_ID()) {
            setScore(1);
        }
    }

    /**
     * FUnction that sets the current score of the user.
     * @param tscore - the value of the score that the player currently has.
     */
    public void setScore(int tscore) {
        mainCtrl.getMultiPlayerGame().setScore(tscore);
        this.score.setText("Your Score:" + "\n" + mainCtrl.getMultiPlayerGame().getPlayer().getScore());
    }

    public void goBackMainMenu() {
        mainCtrl.showMainMenu();
    }

    public void setQuestion(MoreExpensive question) {
        removeStyle();
        enableAllButtons();
        this.question = question;
        this.questionNumber.setText(n + "/" + "20");
        if (n < 20) {
            n++;
        } else if (n == 20) {
            n = 1;
        } else {
            this.score.setText("Your Score:" + "\n" + 0);
        }
        if (question instanceof LessExpensive) {
            this.questionText.setText("What activity takes less energy?");
        } else {
            this.questionText.setText("What activity takes more energy?");
        }

        this.buttonA.setText(question.getOptions()[0].getTitle());
        this.buttonB.setText(question.getOptions()[1].getTitle());
        this.buttonC.setText(question.getOptions()[2].getTitle());

        this.imageA.setImage(new Image(config.getServerLocation() + API_PATH + question.getOptions()[0].getImagePath()));
        this.imageB.setImage(new Image(config.getServerLocation()  + API_PATH + question.getOptions()[1].getImagePath()));
        this.imageC.setImage(new Image(config.getServerLocation() + API_PATH + question.getOptions()[2].getImagePath()));

        this.correctAnswer = question.getAnswer();
    }

    /**
     * Enable all the buttons so the user will be able to answer.
     */
    public void enableAllButtons() {
        buttonA.setDisable(false);
        buttonB.setDisable(false);
        buttonC.setDisable(false);

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
                gameTimer.cancel();
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
    /**
     * Function that colours the answers depending on the right/wrong answer given.
     * @param option - option that was chosen, for aplying the right colours.
     */
    
    public void colorAnswers(int option) {
        if (buttonA.getText().equals(question.getAnswer().getTitle())) {
            buttonA.setStyle("-fx-background-color: #00FF00;");
        } else if (buttonB.getText().equals(question.getAnswer().getTitle())) {
            buttonB.setStyle("-fx-background-color: #00FF00;");
        } else {
            buttonC.setStyle("-fx-background-color: #00FF00;");
        }
        switch (option) {
            case 0:
                if (!buttonA.getText().equals(question.getAnswer().getTitle())) {
                    buttonA.setStyle("-fx-background-color: #FF0000;");
                }
                break;
            case 1:
                if (!buttonB.getText().equals(question.getAnswer().getTitle())) {
                    buttonB.setStyle("-fx-background-color: #FF0000;");
                }
                break;
            case 2:
                if (!buttonC.getText().equals(question.getAnswer().getTitle())) {
                    buttonC.setStyle("-fx-background-color: #FF0000;");
                }
                break;
        }

    }

}
