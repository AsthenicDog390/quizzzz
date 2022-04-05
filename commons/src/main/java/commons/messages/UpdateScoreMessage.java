package commons.messages;

public class UpdateScoreMessage extends Message {
    private int score;

    @SuppressWarnings("unused")
    private UpdateScoreMessage() {
        // for object mapper
    }

    public UpdateScoreMessage(int answer) {
        this.score = answer;
    }

    public int getScore() {
        return score;
    }
}
