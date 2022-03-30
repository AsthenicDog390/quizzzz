package server.game;

import commons.messages.AnswerMessage;
import commons.messages.GameEndedMessage;
import commons.messages.Message;
import commons.messages.NextQuestionMessage;
import commons.questions.Question;
import server.datastructures.MessageQueue;

import java.util.List;
import java.util.UUID;

public class SinglePlayerGame {
    private enum State {
        QUESTION_PERIOD,
        GAME_ENDED,
    }

    private List<Question> questions;
    private UUID id;
    private MessageQueue messageQueue;
    private State state;
    private int currentQuestion;


    public SinglePlayerGame(UUID id, List<Question> questions) {
        if (questions == null) {
            throw new IllegalArgumentException("question list must not be null");
        } else if (questions.size() != 20) {
            throw new IllegalArgumentException("new SinglePlayerGame must receive exactly 20 questions");
        }
        for (int i = 0; i < 20; i++) {
            if (questions.get(i) == null) {
                throw new IllegalArgumentException("all questions must not be null");
            }
        }

        this.questions = questions;
        this.id = id;
        this.messageQueue = new MessageQueue();
        this.currentQuestion = 0;
        this.state = State.QUESTION_PERIOD;
        this.advanceState();
    }

    protected SinglePlayerGame(UUID id) {
        this.id = id;
        this.messageQueue = new MessageQueue();
    }

    private void advanceState() {
        switch (this.state) {
            case QUESTION_PERIOD:
                if (this.currentQuestion >= this.questions.size()) {
                    this.state = State.GAME_ENDED;
                    this.messageQueue.addMessage(new GameEndedMessage());
                    return;
                }
                this.messageQueue.addMessage(new NextQuestionMessage(this.questions.get(this.currentQuestion)));
                this.currentQuestion++;
                break;
        }
    }

    private void providedAnswer(int answer) {
        this.advanceState();
    }

    /**
     * handleMessage handles messages sent from the client to the server.
     * */
    public void handleMessage(Message m) {
        if (m instanceof AnswerMessage) {
            var answer = (AnswerMessage)m;
            this.providedAnswer(answer.getAnswer());
        }
    }

    public MessageQueue getMessageQueue() {
        return this.messageQueue;
    }

    public String getId() {
        return id.toString();
    }
}
