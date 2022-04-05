package commons.messages;

import commons.Player;

import java.util.List;

public class SingleLeaderboardMessage extends Message {
    private List<Player> leaderBoard;

    @SuppressWarnings("unused")
    private SingleLeaderboardMessage() {
        // for object mapper
    }

    public SingleLeaderboardMessage(List<Player> leaderboard) {
        this.leaderBoard = leaderboard;
    }

    public List<Player> getLeaderBoard() {
        return leaderBoard;
    }
}