package commons.exceptions;

import java.util.List;

public class NameAlreadyPickedException extends Exception {
    private final List<String> pickedNames;

    private final String name;

    public NameAlreadyPickedException(String name, List<String> pickedNames) {
        super("name already picked");
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
