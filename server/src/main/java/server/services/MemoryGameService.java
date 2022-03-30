package server.services;

import commons.Activity;
import commons.questions.MoreExpensive;
import commons.questions.Question;
import org.springframework.stereotype.Service;
import server.game.Game;

import java.util.*;

@Service
public class MemoryGameService implements GameService {
    private Map<String, Game> singlePlayerGames;

    public MemoryGameService() {
        this.singlePlayerGames = new HashMap<>();
    }

    @Override
    public Game newGame() {
        var questions = new ArrayList<Question>(20);
        for (int i = 0; i < 20; i++) {
            questions.add(makeQuestion(i));
        }

        UUID uuid = UUID.randomUUID();
        var game = new Game(uuid, questions);
        singlePlayerGames.put(game.getId(), game);

        return game;
    }

    @Override
    public Optional<Game> getGame(String id) {
        return Optional.ofNullable(singlePlayerGames.get(id));
    }

    @Override
    public void gameFinished(String id) {
        singlePlayerGames.remove(id);
    }

    private Activity makeActivity(String s) {
        return new Activity(s, s, s, Integer.parseInt(s), s);
    }

    private Question makeQuestion(int i) {
        var options = new Activity[3];
        options[0] = makeActivity(String.valueOf(i * 3));
        options[1] = makeActivity(String.valueOf(i * 3 + 1));
        options[2] = makeActivity(String.valueOf(i * 3 + 2));
        var answer = options[0];

        return new MoreExpensive(options, answer);
    }
}
