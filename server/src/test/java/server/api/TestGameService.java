package server.api;

import server.game.SinglePlayerGame;
import server.services.GameService;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

public class TestGameService implements GameService {
    HashMap<String, TestSinglePlayerGame> games;

    public TestGameService() {
        this.games = new HashMap<>();
    }

    @Override
    public SinglePlayerGame newSinglePlayerGame() {
        var id = UUID.randomUUID();
        var game = new TestSinglePlayerGame(id, null);

        games.put(id.toString(), game);

        return game;
    }

    @Override
    public Optional<SinglePlayerGame> getSinglePlayerGame(String id) {
        return Optional.ofNullable(games.get(id));
    }

    @Override
    public void singlePlayerGameFinished(String id) {
        games.remove(id);
    }
}
