package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.questions.LessExpensive;
import commons.questions.MoreExpensive;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class MultipleChoiceSingeCtrl {
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
    public MultipleChoiceSingeCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    public void answerA() {
        giveAnswer(0);
    }

    public void answerB() {
        giveAnswer(1);
    }

    public void answerC() {
        giveAnswer(2);
    }

    public void giveAnswer(int answer) {
        mainCtrl.getGame().giveAnswer(answer);
    }

    public void setQuestion(MoreExpensive question) {
        this.question = question;
        if (question instanceof LessExpensive) {
            this.questionText.setText("What activity takes less energy?");
        } else {
            this.questionText.setText("What activity takes more energy?");
        }

        this.buttonA.setText(question.getOptions()[0].title);
        this.buttonB.setText(question.getOptions()[1].title);
        this.buttonC.setText(question.getOptions()[2].title);

        //TODO: Set images
    }
}
