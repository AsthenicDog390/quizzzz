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

    @FXML
    private Label warning;

    /**
     * Generator for the "estimate" type of question controller for a singleplayer game.
     * @param server - the server where the singleplayer game is running on.
     * @param mainCtrl - the main controller where the game runs on.
     */
    @Inject
    public EstimateSingleCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    /**
     * Field where the estimate answer for the question is inputted.
     */
    public void answerField() {
        boolean checkFailed = false;
        try {
            int d = Integer.parseInt(answer.getText());
        } catch (NumberFormatException nfe) {
            answer.setText("");
            warning.setVisible(true);
            checkFailed = true;
        }
        if (!checkFailed) {
            warning.setVisible(false);
            disableAll();
            mainCtrl.getSinglePlayerGame().giveAnswer(Integer.parseInt(answer.getText())); //be careful when receiving the answer in the server
            //TODO: last line causes a null pointer exception
        }
    }

    /**
     * Function that sets the question text in the scene.
     * @param question - the question that is shown.
     */
    public void setQuestion(Estimate question) {
        this.question = question;
        this.questionText.setText("How much energy does: " + question.getActivity().getTitle() + " consume?");

        //TODO: Set images
    }

    /**
     * Function returning the user to the main menu.
     */
    public void goBackMainMenu() {
        mainCtrl.showMainMenu();
    }

    /**
     * Starting 2 timers corresponding to the progress bar and disabling the buttons after a specific period of time.
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
                if (progress > 0.004) {
                    progressBar.setProgress(progress - 0.004);
                }
            }
        };
        gameTimer.schedule(timeOut, 10000);
        progressBarTimer.schedule(lowerBar, 0, 40);
    }

    /**
     * Function that disables the possibility to answer anymore.
     */
    private void disableAll() {
        answer.setDisable(true);
        confirm.setDisable(true);
    }

    /**
     * Function that shows the dialog used for asking the user if he wants to quit the game.
     */
    public void showDialogExit() {
        dialogPane.setVisible(true);
    }

    /**
     * Function that hides the dialog used for asking the user if he wants to quit the game.
     */
    public void hideDialogExit() {
        dialogPane.setVisible(false);
    }

}
