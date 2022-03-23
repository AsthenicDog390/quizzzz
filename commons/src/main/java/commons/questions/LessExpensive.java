package commons.questions;

import commons.Activity;

import java.util.Arrays;

public class LessExpensive extends MoreExpensive {
    @SuppressWarnings("unused")
    private LessExpensive() {
        super();
        // for object mapper
    }

    public LessExpensive(Activity[] options, Activity answer) {
        super(options, answer);
    }

    @Override
    public String toString() {
        return "LessExpensive{" +
                "options=" + Arrays.toString(options) +
                ", answer=" + answer +
                '}';
    }


}
