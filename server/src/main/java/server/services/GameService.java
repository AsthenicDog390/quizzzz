package server.services;

import server.game.Game;

import java.util.Optional;

public interface GameService {
    Game newGame();
    Optional<Game> getGame(String id);
    void gameFinished(String id);
}
