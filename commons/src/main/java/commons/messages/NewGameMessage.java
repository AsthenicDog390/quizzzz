package commons.messages;

public class NewGameMessage extends Message {
    private String id;

    @SuppressWarnings("unused")
    private NewGameMessage() {
        // for object mapper
    }

    public NewGameMessage(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
