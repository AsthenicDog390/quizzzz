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

    /**
     * setConsumer sets the message consumer of the queue.
     * @param consumer The consumer that will be set.
     */
    public void setConsumer(Consumer<Message> consumer) {
        synchronized (this) {
            this.consumer = Optional.of(consumer);

            this.maybeSendMessage();
        }
    }

    /**
     * resetConsumer removes the consumer from the queue.
     */
    public void resetConsumer() {
        synchronized (this) {
            this.consumer = Optional.empty();
        }
    }

    /**
     * addMessage adds a message to the queue.
     * If there is a consumer present it will send the message to the consumer.
     * @param m The message to send to the consumer.
     */
    public void addMessage(Message m) {
        synchronized (this) {
            this.messageQueue.add(m);

            this.maybeSendMessage();
        }
    }

    /**
     * maybeSendMessage sends a message to the consumer if it is present and if there is a message to send.
     * After the message is sent the consumer is reset.
     */
    private void maybeSendMessage() {
        if (this.consumer.isPresent() && this.messageQueue.size() > 0) {
            this.consumer.get().accept(this.messageQueue.poll());
            this.consumer = Optional.empty();
        }
    }

    public int size() {
        return this.messageQueue.size();
    }

    /**
     * getMessages returns all messages currently in the queue.
     * @return A view of all messages in the queue.
     */
    public List<Message> getMessages() {
        synchronized (this) {
            return new ArrayList<>(this.messageQueue);
        }
    }
}
