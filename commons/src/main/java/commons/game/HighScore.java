package commons.game;

import commons.Player;

import javax.persistence.*;

@Entity(name = "HighScore")
@Table(name = "scores")
public class HighScore {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private int score;

    @ManyToOne
    private Player player;
    @ManyToOne
    private Game game;

    @SuppressWarnings("unused")
    private HighScore() {
        // for object mapper
    }

    public HighScore(int score, Player player, Game game) {
        this.score = score;
        this.player = player;
        this.game = game;
    }

    public long getId() {
        return id;
    }

    public int getScore() {
        return score;
    }

    public Player getPlayer() {
        return player;
    }

    public Game getGame() {
        return game;
    }
}
