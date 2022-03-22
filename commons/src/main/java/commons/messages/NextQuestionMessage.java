package commons.messages;

import commons.questions.Question;

public class NextQuestionMessage extends Message {
    private Question question;

    @SuppressWarnings("unused")
    private NextQuestionMessage() {
        // for object mapper
    }

    public NextQuestionMessage(Question question) {
        this.question = question;
    }

    public Question getQuestion() {
        return question;
    }
}
