package commons.messages;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NewGameMessageTest {

    @Test
    void getId() {
        var m = new NewGameMessage("test", "id");
        assertEquals("test", m.getId());
        assertEquals("id", m.getPlayerId());
    }
}