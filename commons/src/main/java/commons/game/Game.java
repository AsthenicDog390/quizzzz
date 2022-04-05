package commons.game;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "Games")
@Table(name = "games")
public class Game {
    @Id
    private String id;

    @SuppressWarnings("unused")
    private Game() {
        // for object mapper
    }

    public Game(String id) {
        this.id = id;

    }

    public String getId() {
        return id;
    }

}
