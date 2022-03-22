package commons.messages;

public class AnswerMessage extends Message {
    private int answer;

    @SuppressWarnings("unused")
    private AnswerMessage() {
        // for object mapper
    }

    public AnswerMessage(int answer) {
        this.answer = answer;
    }

    public int getAnswer() {
        return answer;
    }
}
