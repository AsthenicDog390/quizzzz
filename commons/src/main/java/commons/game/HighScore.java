package commons.game;

import javax.persistence.*;

@Entity(name = "HighScore")
@Table(name = "scores")
public class HighScore {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private int score;


    private String playerId;

    private String gameId;

    @SuppressWarnings("unused")
    private HighScore() {
        // for object mapper
    }

    public HighScore(int score, String playerId, String game) {
        this.score = score;
        this.playerId = playerId;
        this.gameId = gameId;
    }

    public long getId() {
        return id;
    }

    public int getScore() {
        return score;
    }

    public String getPlayerId(){return playerId;}

    public String getGameId() {
        return gameId;
    }
}
