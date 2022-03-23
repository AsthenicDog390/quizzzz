package commons;

import java.util.Objects;

public class Player {
    private String id;
    private String name;
    private String gameId;

    public Player(String id, String name, String gameId) {
        this.id = id;
        this.name = name;
        this.gameId = gameId;
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
