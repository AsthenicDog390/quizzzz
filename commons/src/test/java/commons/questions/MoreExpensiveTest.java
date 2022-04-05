package commons.questions;

import commons.Activity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoreExpensiveTest {

    private Activity getActivity(String name, long id) {
        var a = new Activity(name, name, name, 0L, name);
        a.activity_id = id;
        return a;
    }

    @Test
    void constructorThrows() {
        assertThrows(IllegalArgumentException.class, () -> {
            new MoreExpensive(null, null);
        });
        var one = getActivity("one", 1);
        var two = getActivity("two", 2);
        var three = getActivity("three", 3);
        var four = getActivity("four", 4);

        assertThrows(IllegalArgumentException.class, () -> {
            new MoreExpensive(new Activity[]{one, two, three, four}, null);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new MoreExpensive(new Activity[]{one, two, null}, null);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new MoreExpensive(new Activity[]{one, two, three}, four);
        });
    }

    @Test
    void getOptions() {
        var one = getActivity("one", 1);
        var two = getActivity("two", 2);
        var three = getActivity("three", 3);

        var q = new MoreExpensive(new Activity[]{one, two, three}, three);
        assertArrayEquals(new Activity[]{one, two, three}, q.getOptions());
    }

    @Test
    void getAnswer() {
        var one = getActivity("one", 1);
        var two = getActivity("two", 2);
        var three = getActivity("three", 3);

        var q = new MoreExpensive(new Activity[]{one, two, three}, three);
        assertEquals(three, q.getAnswer());
    }
}