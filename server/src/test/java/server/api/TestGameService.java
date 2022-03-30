package server.api;

import server.game.Game;
import server.services.GameService;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

public class TestGameService implements GameService {
    HashMap<String, TestGame> games;

    public TestGameService() {
        this.games = new HashMap<>();
    }

    @Override
    public Game newGame() {
        var id = UUID.randomUUID();
        var game = new TestGame(id, null);

        games.put(id.toString(), game);

        return game;
    }

    @Override
    public Optional<Game> getGame(String id) {
        return Optional.ofNullable(games.get(id));
    }

    @Override
    public void gameFinished(String id) {
        games.remove(id);
    }
}
