package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.questions.LessExpensive;
import commons.questions.MoreExpensive;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class MultipleChoiceMultiCtrl {
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

    @Inject
    public MultipleChoiceMultiCtrl(ServerUtils server, MainCtrl mainCtrl) {
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
        mainCtrl.getMultiPlayerGame().giveAnswer(answer);
    }

    public void goBackMainMenu() {
        mainCtrl.showMainMenu();
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

    /**
     * Disable all the buttons so the user won't have the option to press multiple answers
     */
    public void disableAllButtons() {
        buttonA.setDisable(true);
        buttonB.setDisable(true);
        buttonC.setDisable(true);
    }

}
