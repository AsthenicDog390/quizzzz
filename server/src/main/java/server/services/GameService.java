package server.services;

import server.game.Game;

import java.util.Optional;

public interface GameService {
    Game newGame(boolean isSinglePlayer);

    Optional<Game> getGame(String id);

    void gameFinished(String id);
}
