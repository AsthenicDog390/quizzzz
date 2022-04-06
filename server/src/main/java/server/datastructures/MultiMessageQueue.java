package server.datastructures;

import commons.messages.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class MultiMessageQueue {
    private final ArrayList<Message> messages;

    private final HashMap<String, Optional<Consumer<Message>>> consumers;

    private final HashMap<String, Integer> indices;

    public MultiMessageQueue() {
        this.messages = new ArrayList<>();
        this.consumers = new HashMap<>();
        this.indices = new HashMap<>();
    }

    /**
     * setConsumer sets the message consumer of the queue.
     *
     * @param consumer The consumer that will be set.
     */
    public void setConsumer(String playerId, Consumer<Message> consumer) {
        synchronized (this) {
            this.consumers.put(playerId, Optional.of(consumer));
            if (!this.indices.containsKey(playerId)) {
                this.indices.put(playerId, 0);
            }

            this.maybeSendMessage();
        }
    }

    /**
     * resetConsumer removes the consumer from the queue.
     */
    public void resetConsumer(String playerId) {
        synchronized (this) {
            this.consumers.put(playerId, Optional.empty());
        }
    }

    /**
     * addMessage adds a message to the queue.
     * If there is a consumer present it will send the message to the consumer.
     *
     * @param m The message to send to the consumer.
     */
    public void addMessage(Message m) {
        synchronized (this) {
            this.messages.add(m);

            this.maybeSendMessage();
        }
    }

    /**
     * maybeSendMessage sends a message to the consumer if it is present and if there is a message to send.
     * After the message is sent the consumer is reset.
     */
    private void maybeSendMessage() {
        for (String id : this.consumers.keySet()) {
            var consumer = this.consumers.get(id);
            int index = this.indices.get(id);
            if (consumer.isPresent() && this.messages.size() > index) {
                consumer.get().accept(this.messages.get(index));
                this.consumers.put(id, Optional.empty());
                this.indices.put(id, index + 1);
            }
        }
    }

    public int size() {
        return this.messages.size();
    }

    /**
     * getMessages returns all messages currently in the queue.
     *
     * @return A view of all messages in the queue.
     */
    public List<Message> getMessages() {
        synchronized (this) {
            return new ArrayList<>(this.messages);
        }
    }
}
