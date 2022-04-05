package server.api;

import commons.messages.SendNameMessage;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import server.datastructures.TestMessage;

import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest {

//    @Test
//    void newSinglePlayerGame() {
//        var serv = new TestGameService();
//        var sut = new GameController(serv);
//
//        var msg = sut.newSinglePlayerGame(new SendNameMessage("someName"));
//        assertTrue(serv.getGame(msg.getId()).isPresent());
//    }

//    @Test
//    void getSinglePlayerGameEvents() {
//        var serv = new TestGameService();
//        var sut = new GameController(serv);
//
//        var id = sut.newSinglePlayerGame().getId();
//        serv.getSinglePlayerGame(id).get().addMessage("test", new TestMessage(0));
//        var res = (ResponseEntity<Message>)sut.getSinglePlayerGameEvents(id).getResult();
//        assertEquals(HttpStatus.OK, res.getStatusCode());
//        assertEquals(new TestMessage(0), res.getBody());
//    }

//    @Test
//    void sendMessage() {
//        var serv = new TestGameService();
//        var sut = new GameController(serv);
//
//        var id = sut.newSinglePlayerGame(new SendNameMessage("someName")).getId();
//        var res = sut.sendMessageSinglePlayer(id, new TestMessage(0));
//        assertEquals(ResponseEntity.ok().build(), res);
//        assertEquals(new TestMessage(0), ((TestGame)serv.getGame(id).get()).getHandlesMessages().get(0));
//    }

    @Test
    void getSinglePlayerGameEventsNotFound() {
        var serv = new TestGameService();
        var sut = new GameController(serv);

        var id = "test";
        var res = sut.getSinglePlayerGameEvents(id);
        assertEquals(ResponseEntity.notFound().build(), res.getResult());
    }

    @Test
    void sendMessageNotFound() {
        var serv = new TestGameService();
        var sut = new GameController(serv);

        var id = "test";
        var res = sut.sendMessageSinglePlayer(id, new TestMessage(0));
        assertEquals(ResponseEntity.notFound().build(), res);
    }
}