package commons;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Objects;

@Entity(name = "Player")
@Table(name = "players")
public class Player {
    @Id
    private String id;

    private String name;

    private String gameId;

    private boolean isSingleplayer;

    @Transient
    private int score;

    @SuppressWarnings("unused")
    private Player() {
        // for object mapper
    }

    public Player(String id, String name, boolean singleplayer) {
        this.id = id;
        this.name = name;
        this.gameId = "unknown";
        this.score = 0;
        this.isSingleplayer = singleplayer;
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

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return this.score;
    }

    public boolean getIsSingleplayer() {
        return this.isSingleplayer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Player player = (Player) o;
        return Objects.equals(id, player.id) && Objects.equals(name, player.name) && Objects.equals(gameId, player.gameId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, gameId);
    }

    @Override
    public String toString() {
        return "Player{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", gameId='" + gameId + '\'' +
            ", score=" + score +
            '}';
    }

}
