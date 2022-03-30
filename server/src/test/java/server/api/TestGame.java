package server.api;

import commons.messages.Message;
import commons.questions.Question;
import server.game.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TestGame extends Game {
    public TestGame(UUID id, List<Question> questions) {
        super(id);
        this.handledMessages = new ArrayList<>();
    }

    private ArrayList<Message> handledMessages;

    @Override
    public void handleMessage(String PlayerId, Message m) {
        this.handledMessages.add(m);
    }

    public List<Message> getHandlesMessages() {
        return this.handledMessages;
    }
}
