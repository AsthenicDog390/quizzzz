package server.services;

import server.game.SinglePlayerGame;

import java.util.Optional;

public interface GameService {
    public SinglePlayerGame newSinglePlayerGame();
    public Optional<SinglePlayerGame> getSinglePlayerGame(String id);
    public void singlePlayerGameFinished(String id);
}
