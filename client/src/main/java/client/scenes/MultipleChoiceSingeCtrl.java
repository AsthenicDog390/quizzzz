package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.questions.MoreExpensive;

public class MultipleChoiceSingeCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    private MoreExpensive question;

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
    }
}
