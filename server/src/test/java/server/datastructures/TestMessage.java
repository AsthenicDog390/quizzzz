package server.datastructures;

import commons.messages.Message;

import java.util.Objects;

public class TestMessage extends Message {
    private final int id;

    public TestMessage(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TestMessage that = (TestMessage) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
