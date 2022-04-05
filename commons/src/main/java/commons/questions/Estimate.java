package commons.questions;

import commons.Activity;

public class Estimate extends Question {
    protected Activity activity;

    protected long answer;

    @SuppressWarnings("unused")
    private Estimate() {
        // for object mapper
    }

    public Estimate(Activity activity) {
        if (activity == null) {
            throw new IllegalArgumentException("parameters to constructor may not be null");
        }
        this.activity = activity;
        this.answer = activity.getConsumptionInWh();
    }

    public Activity getActivity() {
        return activity;
    }

    public long getAnswer() {
        return answer;
    }

    @Override
    public String toString() {
        return "Estimate{" +
            "activity=" + activity.toString() +
            ", answer=" + answer +
            '}';
    }
}
