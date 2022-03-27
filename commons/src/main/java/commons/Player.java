package commons;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "Player")
@Table(name = "players")
public class Player {
    @Id
    private String id;
    private String name;
    private String gameId;

    @Transient
    private int score;

    @SuppressWarnings("unused")
    private Player() {
        // for object mapper
    }

    public Player(String id, String name) {
        this.id = id;
        this.name = name;
        this.gameId = "unknown";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public void setScore(int score) { this.score = score; }

    public int getScore() { return this.score; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(id, player.id) && Objects.equals(name, player.name) && Objects.equals(gameId, player.gameId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, gameId);
    }
}
