package commons.messages;

import java.util.List;

public class NameAlreadyPickedMessage extends Message {
    private List<String> pickedNames;

    private String name;

    @SuppressWarnings("unused")
    private NameAlreadyPickedMessage() {
        // for object mapper
    }

    public NameAlreadyPickedMessage(String name, List<String> pickedNames) {
        this.name = name;
        this.pickedNames = pickedNames;
    }

    public String getName() {
        return name;
    }

    public List<String> getPickedNames() {
        return pickedNames;
    }
}
