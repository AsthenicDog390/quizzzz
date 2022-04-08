package server.api;

import commons.exceptions.NameAlreadyPickedException;
import commons.messages.*;
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

    private final Object waitingRoomMutex = new Object();

    private Game waitingRoom;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/singleplayer/new")
    public NewGameMessage newSinglePlayerGame(@RequestBody SendNameMessage nameRetrieve) {
        Game newGame = gameService.newGame(true);

        newGame.start();

        try {
            var p = newGame.addPlayer(nameRetrieve.getToBePassedName(), newGame.getId(), true);
            return new NewGameMessage(newGame.getId(), p.getId());
        } catch (NameAlreadyPickedException e) {
            throw new RuntimeException("code path should be unreachable in singleplayer game", e);
        }
    }

    @GetMapping("/singleplayer/{id}")
    public DeferredResult<ResponseEntity<Message>> getSinglePlayerGameEvents(@PathVariable("id") String id) {
        DeferredResult<ResponseEntity<Message>> deferredResult = new DeferredResult<>(12000l);
        var maybeGame = gameService.getGame(id);
        if (maybeGame.isEmpty()) {
            deferredResult.setResult(ResponseEntity.notFound().build());
        } else {
            var game = maybeGame.get();
            game.addMessageConsumer(id, m -> {
                deferredResult.setResult(ResponseEntity.ok(m));
            });
            deferredResult.onTimeout(() -> {
                game.resetConsumer(id);
                deferredResult.setResult(ResponseEntity.ok(new NoUpdateMessage()));
            });
        }

        return deferredResult;
    }

    @PostMapping("/multiplayer/new")
    public Message newMultiPlayerGame(@RequestBody SendNameMessage nameRetrieve) {
        synchronized (waitingRoomMutex) {
            if (waitingRoom == null) {
                waitingRoom = gameService.newGame(false);
            }

            try {
                var p = waitingRoom.addPlayer(nameRetrieve.getToBePassedName(), UUID.randomUUID().toString(), false);
                return new NewGameMessage(waitingRoom.getId(), p.getId());
            } catch (NameAlreadyPickedException e) {
                return new NameAlreadyPickedMessage(e.getName(), e.getPickedNames());
            }
        }
    }

    @GetMapping("/multiplayer/{gameId}/{playerId}")
    public DeferredResult<ResponseEntity<Message>> getMultiPlayerGameEvents(@PathVariable("gameId") String gameId, @PathVariable("playerId") String playerId) {
        DeferredResult<ResponseEntity<Message>> deferredResult = new DeferredResult<>(12000l);
        var maybeGame = gameService.getGame(gameId);
        if (maybeGame.isEmpty()) {
            deferredResult.setResult(ResponseEntity.notFound().build());
        } else {
            var game = maybeGame.get();
            game.addMessageConsumer(playerId, m -> {
                deferredResult.setResult(ResponseEntity.ok(m));
            });
            deferredResult.onTimeout(() -> {
                game.resetConsumer(playerId);
                deferredResult.setResult(ResponseEntity.ok(new NoUpdateMessage()));
            });
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

    @GetMapping("/multiplayer/{gameId}/start")
    public ResponseEntity startGame(@PathVariable("gameId") String gameId) {
        synchronized (waitingRoomMutex) {
            if (waitingRoom == null) {
                return ResponseEntity.notFound().build();
            } else if (!waitingRoom.getId().equals(gameId)) {
                return ResponseEntity.notFound().build();
            }
            waitingRoom.start();
            waitingRoom = gameService.newGame(false);

            return ResponseEntity.ok().build();
        }
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
