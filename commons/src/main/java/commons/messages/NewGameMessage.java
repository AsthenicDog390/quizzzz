package commons.messages;

public class NewGameMessage extends Message {
    private String id;
    private String playerId;

    @SuppressWarnings("unused")
    private NewGameMessage() {
        // for object mapper
    }

    public NewGameMessage(String id, String playerId) {
        this.id = id;
        this.playerId = playerId;
    }

    public String getId() {
        return id;
    }
    public String getPlayerId() { return playerId; }
}
