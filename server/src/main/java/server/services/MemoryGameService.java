package server.services;

import Services.QuestionBuilder;
import commons.Activity;
import commons.questions.MoreExpensive;
import commons.questions.Question;
import org.springframework.stereotype.Service;
import server.database.ActivityRepository;
import server.database.GameRepository;
import server.database.PlayerRepository;
import server.database.ScoreRepository;
import server.game.Game;

import java.util.*;

@Service
public class MemoryGameService implements GameService {
    private final GameRepository gameRepository;

    private final ScoreRepository scoreRepository;

    private final PlayerRepository playerRepository;

    private final Random random;

    private final Map<String, Game> singlePlayerGames;

    private final QuestionBuilder questionBuilder;

    public MemoryGameService(
        GameRepository gameRepository,
        PlayerRepository playerRepository,
        ScoreRepository scoreRepository,
        ActivityRepository activityRepository,
        Random random
    ) {
        this.singlePlayerGames = new HashMap<>();
        this.gameRepository = gameRepository;
        this.playerRepository = playerRepository;
        this.scoreRepository = scoreRepository;
        this.questionBuilder = new QuestionBuilder(activityRepository);
        this.random = random;
    }

    @Override
    public Game newGame(boolean isSinglePlayer) {
        var questions = new ArrayList<Question>(20);

        for (int i = 0; i < 20; i++) {
            int val = Math.abs(random.nextInt()) % 2;
            Question question;
            switch (val) {
                case 1:
                    question = questionBuilder.generateMoreExpensiveQuestion();
                    break;
                default:
                    question = questionBuilder.generateLessExpensiveQuestion();
                    break;
            }

            questions.add(question);
        }

        UUID uuid = UUID.randomUUID();
        var game = new Game(isSinglePlayer, uuid, questions, gameRepository, playerRepository, scoreRepository, new TimerService());
        singlePlayerGames.put(game.getId(), game);

        var obj = new commons.game.Game(uuid.toString());
        gameRepository.save(obj);

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
