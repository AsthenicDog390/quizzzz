package commons.messages;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnswerMessageTest {

    @Test
    void getAnswer() {
        var m = new AnswerMessage(42);
        assertEquals(42, m.getAnswer());
    }
}