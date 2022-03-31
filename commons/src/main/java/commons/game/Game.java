package commons.game;

import commons.Player;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Games")
@Table(name = "games")
public class Game {
    @Id
    private String id;
    @OneToMany(mappedBy = "gameId")
    private List<Player> players;
    @OneToMany(mappedBy = "game")
    private List<HighScore> scores;

    @SuppressWarnings("unused")
    private Game() {
        // for object mapper
    }

    public Game(String id) {
        this.id = id;
        this.players = new ArrayList<>();
        this.scores = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public List<HighScore> getScores() {
        return scores;
    }

    public List<Player> getPlayers() {
        return players;
    }
}
