package server.datastructures;

import commons.messages.Message;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class MessageQueueTest {

    @Test
    void setConsumer() {
        var queue = new MessageQueue();
        var cons = new TestConsumer();
        queue.setConsumer(cons);

        assertEquals(Optional.empty(), cons.getMessage());

        queue.addMessage(new TestMessage(0));
        assertEquals(new TestMessage(0), cons.getMessage().get());

        queue.addMessage(new TestMessage(1));
        cons = new TestConsumer();
        queue.setConsumer(cons);

        assertEquals(new TestMessage(1), cons.getMessage().get());
    }

    @Test
    void resetConsumer() {
        var queue = new MessageQueue();
        var cons = new TestConsumer();
        queue.setConsumer(cons);
        queue.resetConsumer();
        queue.addMessage(new TestMessage(0));

        assertEquals(Optional.empty(), cons.getMessage());
    }

    @Test
    void addMessage() {
        var queue = new MessageQueue();
        assertEquals(0, queue.size());
        queue.addMessage(new TestMessage(0));
        assertEquals(1, queue.size());
        queue.addMessage(new TestMessage(1));
        assertEquals(2, queue.size());
    }
}