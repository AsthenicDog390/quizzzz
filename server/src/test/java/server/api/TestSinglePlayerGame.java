package server.api;

import commons.messages.Message;
import commons.questions.Question;
import server.datastructures.MessageQueue;
import server.game.SinglePlayerGame;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TestSinglePlayerGame extends SinglePlayerGame {
    public TestSinglePlayerGame(UUID id, List<Question> questions) {
        super(id);
        this.handledMessages = new ArrayList<>();
    }

    private ArrayList<Message> handledMessages;

    @Override
    public void handleMessage(Message m) {
        this.handledMessages.add(m);
    }

    @Override
    public MessageQueue getMessageQueue() {
        return super.getMessageQueue();
    }

    public List<Message> getMessages() {
        return getMessageQueue().getMessages();
    }

    public List<Message> getHandlesMessages() {
        return this.handledMessages;
    }
}
