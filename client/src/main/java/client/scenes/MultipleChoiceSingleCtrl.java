package client.scenes;

import client.utils.Config;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Activity;
import commons.questions.LessExpensive;
import commons.questions.MoreExpensive;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class MultipleChoiceSingleCtrl {
    private final ServerUtils server;

    private final MainCtrl mainCtrl;

    private final Config config;

    private MoreExpensive question;

    private Timer gameTimer = new Timer();

    private Timer progressBarTimer = new Timer();

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
    private Label questionText;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private DialogPane dialogPane;

    @FXML
    private Button YesExit;

    @FXML
    private Button NoExit;

    @FXML
    private ImageView imageA;

    @FXML
    private ImageView imageB;

    @FXML
    private ImageView imageC;

    private Integer n;

    private Activity correctAnswer;

    private static final String API_PATH = "/images/";

    /**
     * Constructor for the multiple choice type of question screen controller.
     * @param server - the server where the singleplayer game is running on.
     * @param mainCtrl - the main controller where the game runs on.
     */
    @Inject
    public MultipleChoiceSingleCtrl( Config config, ServerUtils server, MainCtrl mainCtrl) {
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
        giveAnswer(0);
        colorAnswers(0);
        cancelTimer();
    }

    /**
     * Function used for selecting the middle answer.
     */
    public void answerB() {
        disableAllButtons();
        giveAnswer(1);
        colorAnswers(1);
        cancelTimer();
    }

    /**
     * Function used for selecting the most right answer.
     */
    public void answerC() {
        disableAllButtons();
        giveAnswer(2);
        colorAnswers(2);
        cancelTimer();
    }

    /**
     * Function that cancels the front-end timer on the question.
     */
    public void cancelTimer() {
        gameTimer.cancel();
        progressBarTimer.cancel();
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

    /**
     * Function that sends the given answer to the SinglePlayerGame instance, so that it can be submitted.
     * @param answer - the answer that was chosen.
     */
    public void giveAnswer(int answer) {
        mainCtrl.getSinglePlayerGame().giveAnswer(answer);
        if (correctAnswer.getActivity_ID() == question.getOptions()[answer].getActivity_ID()) {
            mainCtrl.getSinglePlayerGame().updScore(1);
            setScore(1);
        }
    }

    /**
     * FUnction that sets the current score of the user.
     * @param tscore - the value of the score that the player currently has.
     */
    public void setScore(int tscore) {
        mainCtrl.getSinglePlayerGame().setScore(tscore);
        this.score.setText("Your Score:" + "\n" + mainCtrl.getSinglePlayerGame().getPlayer().getScore());
    }

    /**
     * Function that initializes the label where the current score is displayed.
     */
    public void initializeScoreLabel() {
        this.score.setText("Your Score:" + "\n" + 1);
    }

    /**
     * Setter for the incoming next question, so that the scene will display it and the number of it.
     * @param question - the question that will be showed.
     */
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
        this.imageB.setImage(new Image(config.getServerLocation() + API_PATH + question.getOptions()[1].getImagePath()));
        this.imageC.setImage(new Image(config.getServerLocation() + API_PATH + question.getOptions()[2].getImagePath()));

        this.correctAnswer = question.getAnswer();
    }

    /**
     * Function that ends the game at any time, not submitting the score and redirects the player to the main menu.
     */
    public void goBackMainMenu() {
        dialogPane.setVisible(false);
        cancelTimer();
        mainCtrl.gameEnded();
        mainCtrl.showMainMenu();
    }

    /**
     * Removes the background colors from the buttons.
     */
    public void removeStyle() {
        buttonA.setStyle(null);
        buttonB.setStyle(null);
        buttonC.setStyle(null);
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
     * Enable all the buttons so the user will be able to answer.
     */
    public void enableAllButtons() {
        buttonA.setDisable(false);
        buttonB.setDisable(false);
        buttonC.setDisable(false);

    }

    /**
     * Function that shows up the dialog that asks the user for quitting the game.
     */
    public void showDialogExit() {
        dialogPane.setVisible(true);
    }

    /**
     * Function that hides up the dialog that asks the user for quitting the game.
     */
    public void hideDialogExit() {
        dialogPane.setVisible(false);
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
}
