package server.datastructures;

import commons.messages.Message;


import java.util.Optional;
import java.util.function.Consumer;

public class TestConsumer implements Consumer<Message> {

    private Optional<Message> message = Optional.empty();

    @Override
    public void accept(Message message) {
        this.message = Optional.of(message);
    }

    public Optional<Message> getMessage() {
        return this.message;
    }
}
