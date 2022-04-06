package commons.messages;

import commons.Player;

import java.util.ArrayList;
import java.util.List;

public class UpdatePlayersMessage extends Message {
    private final List<Player> players;

    @SuppressWarnings("unused")
    private UpdatePlayersMessage() {
        // for object mapper
        this.players = new ArrayList<>();
    }

    public UpdatePlayersMessage(List<Player> players) {
        this.players = players;
    }

    public List<Player> getPlayers() {
        return this.players;
    }
}
