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

    private final ArrayList<Message> handledMessages;

    @Override
    public void handleMessage(String PlayerId, Message m) {
        this.handledMessages.add(m);
    }

//    @Override
//    /**
//     * addPlayer adds a player with a certain name to the game
//     * @param name the name of the player to add
//     * @return the newly created player
//     */
//    public Player addPlayer(String name, String singleplayer) {
//        var p = new Player(UUID.randomUUID().toString(), name, true);
//        p.setGameId("test");
//
//        return p;
//    }

    public List<Message> getHandlesMessages() {
        return this.handledMessages;
    }
}
