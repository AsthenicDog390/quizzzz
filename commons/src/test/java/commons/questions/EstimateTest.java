package commons.questions;

import commons.Activity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EstimateTest {
    private Activity getActivity(String name, long consumption, long id) {
        var a = new Activity(name, name, name, consumption, name);
        a.activity_id = id;
        return a;
    }

    @Test
    void constructorThrowsTest() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Estimate(null);
        });
    }

    @Test
    void getActivityTest() {
        var one = getActivity("one", 5L, 1);
        var two = getActivity("two", 25L, 2);

        var q = new Estimate(one);
        assertEquals(one, q.getActivity());
        assertNotEquals(two, q.getActivity());
    }

    @Test
    void getAnswerTest() {
        var one = getActivity("one", 25L, 1);
        var two = getActivity("two", 34L,2);

        var q = new Estimate(one);
        assertEquals(25L, q.getAnswer());
        q = new Estimate(two);
        assertEquals(34L, q.getAnswer());
    }

}
