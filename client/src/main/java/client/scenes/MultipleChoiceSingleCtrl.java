package client.scenes;

import client.utils.ServerUtils;
import commons.Activity;
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

    private static final String SERVER = "http://localhost:8080/";
    private static final String API_PATH = "images/";

    @Inject
    public MultipleChoiceSingleCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.n =1;
    }


    public void answerA() {
//        disableAllButtons();
        giveAnswer(0);
    }

    public void answerB() {
//        disableAllButtons();
        giveAnswer(1);
    }

    public void answerC() {
//        disableAllButtons();
        giveAnswer(2);
    }

    public void giveAnswer(int answer) {
        mainCtrl.getSinglePlayerGame().giveAnswer(answer);
        if(correctAnswer.getActivity_ID() == question.getOptions()[answer].getActivity_ID()) {
            mainCtrl.getSinglePlayerGame().updScore(1);
            setScore(1);
        }
    }


    public void setScore(int tscore){
        mainCtrl.getSinglePlayerGame().setScore(tscore);
        this.score.setText("Your Score:"+"\n"+ mainCtrl.getSinglePlayerGame().getPlayer().getScore());
    }

    public void initializeScoreLabel(){
        this.score.setText("Your Score:"+"\n"+ 1);
    }

    public void setQuestion(MoreExpensive question) {
        this.question = question;
        this.questionNumber.setText(n+"/"+"20");
        if (n<20) {
            n++;
        }else if(n==20){
            n=1;
        }
        else{
            this.score.setText("Your Score:"+"\n"+0);}
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

        this.correctAnswer = question.getAnswer();
    }

    public void goBackMainMenu() {
        mainCtrl.gameEnded();
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
