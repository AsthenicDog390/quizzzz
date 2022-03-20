package server.api;

import commons.messages.Message;
import commons.messages.NewGameMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import server.game.SinglePlayerGame;
import server.services.GameService;

@RestController
@RequestMapping("/api/games")
public class GameController {
    private GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/singleplayer/new")
    public NewGameMessage newSinglePlayerGame() {
        SinglePlayerGame newGame = gameService.newSinglePlayerGame();

        return new NewGameMessage(newGame.getId());
    }

    @GetMapping("/singleplayer/{id}")
    public DeferredResult<ResponseEntity<Message>> getSinglePlayerGameEvents(@PathVariable("id") String id) {
        DeferredResult<ResponseEntity<Message>> deferredResult = new DeferredResult<>();
        var maybeGame = gameService.getSinglePlayerGame(id);
        if (maybeGame.isEmpty()) {
            deferredResult.setResult(ResponseEntity.notFound().build());
        } else {
            var game = maybeGame.get();
            game.getMessageQueue().setConsumer(m -> {
                deferredResult.setResult(ResponseEntity.ok(m));
            });
            deferredResult.onTimeout(() -> game.getMessageQueue().resetConsumer());
        }

        return deferredResult;
    }

    @PostMapping("/singleplayer/{id}")
    public ResponseEntity sendMessage(@PathVariable("id") String id, @RequestBody Message m) {
        DeferredResult<ResponseEntity> deferredResult = new DeferredResult<>();
        var maybeGame = gameService.getSinglePlayerGame(id);
        if (maybeGame.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        maybeGame.get().handleMessage(m);

        return ResponseEntity.ok().build();
    }
}
