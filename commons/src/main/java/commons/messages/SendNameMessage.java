package commons.messages;

public class SendNameMessage {

    private String toBePassedName;

    /**
     * For object mapper
     */
    private SendNameMessage() {
    }

    public SendNameMessage(String givenName) {
        this.toBePassedName = givenName;
    }

    public String getToBePassedName() {
        return toBePassedName;
    }
}
