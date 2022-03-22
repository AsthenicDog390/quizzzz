package commons.messages;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NewGameMessageTest {

    @Test
    void getId() {
        var m = new NewGameMessage("test");
        assertEquals("test", m.getId());
    }
}