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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MultipleChoiceSingleCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    private MoreExpensive question;

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

    private static final String SERVER = "http://localhost:8080/";
    private static final String API_PATH = "images/";

    @Inject
    public MultipleChoiceSingleCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    public void answerA() {
        disableAllButtons();
        giveAnswer(0);
        colorAnswers(0);
    }

    public void answerB() {
        disableAllButtons();
        giveAnswer(1);
        colorAnswers(1);
    }

    public void answerC() {
        disableAllButtons();
        giveAnswer(2);
        colorAnswers(2);
    }

    public void colorAnswers( int option ) {
        if(buttonA.getText().equals(question.getAnswer().getTitle())) {
            buttonA.setStyle("-fx-background-color: #00FF00;");
        } else if(buttonB.getText().equals(question.getAnswer().getTitle())) {
            buttonB.setStyle("-fx-background-color: #00FF00;");
        } else {
            buttonC.setStyle("-fx-background-color: #00FF00;");
        }
        switch (option) {
            case 0:
                if(!buttonA.getText().equals(question.getAnswer().getTitle())) {
                    buttonA.setStyle("-fx-background-color: #FF0000;");
                }
                break;
            case 1:
                if(!buttonB.getText().equals(question.getAnswer().getTitle())) {
                    buttonB.setStyle("-fx-background-color: #FF0000;");
                }
                break;
            case 2:
                if(!buttonC.getText().equals(question.getAnswer().getTitle())) {
                    buttonC.setStyle("-fx-background-color: #FF0000;");
                }
                break;
        }
    }

    public void giveAnswer(int answer) {
        mainCtrl.getSinglePlayerGame().giveAnswer(answer);
    }

    public void setQuestion(MoreExpensive question) {
        removeStyle();
        enableAllButtons();
        this.question = question;
        if (question instanceof LessExpensive) {
            this.questionText.setText("What activity takes less energy?");
        } else {
            this.questionText.setText("What activity takes more energy?");
        }

        this.buttonA.setText(question.getOptions()[0].getTitle());
        this.buttonB.setText(question.getOptions()[1].getTitle());
        this.buttonC.setText(question.getOptions()[2].getTitle());

        var url = SERVER + API_PATH + question.getOptions()[0].getImagePath();
        this.imageA.setImage(new Image(url));
        this.imageB.setImage(new Image(SERVER + API_PATH + question.getOptions()[1].getImagePath()));
        this.imageC.setImage(new Image(SERVER + API_PATH + question.getOptions()[2].getImagePath()));
    }

    public void goBackMainMenu() {
        mainCtrl.showMainMenu();
    }

    /**
     * Removes the background colors from the buttons
     */
    public void removeStyle(){
        buttonA.setStyle(null);
        buttonB.setStyle(null);
        buttonC.setStyle(null);
    }

    /**
     * Disable all the buttons so the user won't have the option to press multiple answers
     */
    public void disableAllButtons() {
        buttonA.setDisable(true);
        buttonB.setDisable(true);
        buttonC.setDisable(true);
    }

    public void enableAllButtons() {
        buttonA.setDisable(false);
        buttonB.setDisable(false);
        buttonC.setDisable(false);

    }

    /**
     * Starting 2 timers corresponding to the progress bar and disabling the buttons after a specific period of time
     */

    public void showDialogExit() {
        dialogPane.setVisible(true);
    }

    public void hideDialogExit() {
        dialogPane.setVisible(false);
    }

}
