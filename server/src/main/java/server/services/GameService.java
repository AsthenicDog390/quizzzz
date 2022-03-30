package server.services;

import server.game.Game;

import java.util.Optional;

public interface GameService {
    public Game newGame();
    public Optional<Game> getGame(String id);
    public void gameFinished(String id);
}
