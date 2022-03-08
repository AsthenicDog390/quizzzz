package commons.questions;

import commons.Activity;

public class MoreExpensive extends Question {
    private Activity[] options;
    private Activity answer;

    public MoreExpensive(Activity[] options, Activity answer) {
        if (options == null || answer == null) {
            throw new IllegalArgumentException("parameters to constructor may not be null");
        } else if (options.length != 3) {
            throw new IllegalArgumentException("question must have 3 options");
        }

        boolean found = false;
        for (Activity a: options) {
            if (a == null) {
                throw new IllegalArgumentException("all options must not be null");
            }
            if (a.equals(answer)) {
                found = true;
                break;
            }
        }

        if (!found) {
            throw new IllegalArgumentException("answer must be in options array");
        }

        this.options = options;
        this.answer = answer;
    }

    public Activity[] getOptions() {
        return options;
    }

    public Activity getAnswer() {
        return answer;
    }
}
