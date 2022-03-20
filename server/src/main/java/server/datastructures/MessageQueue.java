package server.datastructures;

import commons.messages.Message;

import java.util.*;
import java.util.function.Consumer;

public class MessageQueue {
    private Queue<Message> messageQueue;

    private Optional<Consumer<Message>> consumer;

    public MessageQueue() {
        this.messageQueue = new LinkedList<>();
        this.consumer = Optional.empty();
    }

    public void setConsumer(Consumer<Message> consumer) {
        synchronized (this) {
            this.consumer = Optional.of(consumer);

            this.maybeSendMessage();
        }
    }

    public void resetConsumer() {
        synchronized (this) {
            this.consumer = Optional.empty();
        }
    }

    public void addMessage(Message m) {
        synchronized (this) {
            this.messageQueue.add(m);

            this.maybeSendMessage();
        }
    }

    private void maybeSendMessage() {
        if (this.consumer.isPresent() && this.messageQueue.size() > 0) {
            this.consumer.get().accept(this.messageQueue.poll());
            this.consumer = Optional.empty();
        }
    }

    public int size() {
        return this.messageQueue.size();
    }

    public List<Message> getMessages() {
        return new ArrayList<>(this.messageQueue);
    }
}
