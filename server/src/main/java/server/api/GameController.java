package server.api;

import commons.messages.Message;
import commons.messages.NewGameMessage;
import commons.messages.SendNameMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import server.game.Game;
import server.services.GameService;

import java.util.UUID;

@RestController
@RequestMapping("/api/games")
public class GameController {
    private final GameService gameService;

    private Game waitingRoom;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/singleplayer/new")
    public NewGameMessage newSinglePlayerGame(@RequestBody SendNameMessage nameRetrieve) {
        Game newGame = gameService.newGame();

        var p = newGame.addPlayer(nameRetrieve.getToBePassedName(), newGame.getId(), true);

        return new NewGameMessage(newGame.getId(), p.getId());
    }

    @GetMapping("/singleplayer/{id}")
    public DeferredResult<ResponseEntity<Message>> getSinglePlayerGameEvents(@PathVariable("id") String id) {
        DeferredResult<ResponseEntity<Message>> deferredResult = new DeferredResult<>();
        var maybeGame = gameService.getGame(id);
        if (maybeGame.isEmpty()) {
            deferredResult.setResult(ResponseEntity.notFound().build());
        } else {
            var game = maybeGame.get();
            game.addMessageConsumer(id, m -> {
                deferredResult.setResult(ResponseEntity.ok(m));
            });
            deferredResult.onTimeout(() -> game.resetConsumer(id));
        }

        return deferredResult;
    }

    @GetMapping("/multiplayer/new")
    public NewGameMessage newMultiPlayerGame() {
        if (waitingRoom == null) {
            waitingRoom = gameService.newGame();
            waitingRoom.start();
        }

        var p = waitingRoom.addPlayer("test", UUID.randomUUID().toString(), false);

        return new NewGameMessage(waitingRoom.getId(), p.getId());
    }

    @GetMapping("/multiplayer/{gameId}/{playerId}")
    public DeferredResult<ResponseEntity<Message>> getMultiPlayerGameEvents(@PathVariable("gameId") String gameId, @PathVariable("playerId") String playerId) {
        DeferredResult<ResponseEntity<Message>> deferredResult = new DeferredResult<>();
        var maybeGame = gameService.getGame(gameId);
        if (maybeGame.isEmpty()) {
            deferredResult.setResult(ResponseEntity.notFound().build());
        } else {
            var game = maybeGame.get();
            game.addMessageConsumer(playerId, m -> {
                deferredResult.setResult(ResponseEntity.ok(m));
            });
            deferredResult.onTimeout(() -> game.resetConsumer(playerId));
        }

        return deferredResult;
    }

    @PostMapping("/singleplayer/{id}")
    public ResponseEntity sendMessageSinglePlayer(@PathVariable("id") String id, @RequestBody Message m) {
        DeferredResult<ResponseEntity> deferredResult = new DeferredResult<>();
        var maybeGame = gameService.getGame(id);
        if (maybeGame.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        maybeGame.get().handleMessage(id, m);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/multiplayer/{gameId}/{playerId}")
    public ResponseEntity sendMessageMultiPlayer(@PathVariable("gameId") String gameId, @PathVariable("playerId") String playerId, @RequestBody Message m) {
        DeferredResult<ResponseEntity> deferredResult = new DeferredResult<>();
        var maybeGame = gameService.getGame(gameId);
        if (maybeGame.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        maybeGame.get().handleMessage(playerId, m);

        return ResponseEntity.ok().build();
    }

    // @GetMapping
}
