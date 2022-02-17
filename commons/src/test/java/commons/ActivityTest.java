package commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActivityTest {
    @Test
    public void checkConstructor() {
        var a = new Activity("id", "test", 40, "source");
        assertEquals(a.id, "id");
        assertEquals(a.title, "test");
        assertEquals(a.consumptionInWh, 40);
        assertEquals(a.source, "source");
    }

    @Test
    public void equalsHashCode() {
        var a = new Activity("id", "test", 40, "source");
        var b = new Activity("id", "test", 40, "source");
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void notEqualsHashCode() {
        var a = new Activity("id", "test", 40, "source");
        var b = new Activity("id", "test", 30, "source");
        assertNotEquals(a, b);
        assertNotEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void hasToString() {
        var actual = new Activity("id", "test", 40, "source").toString();
        assertTrue(actual.contains(Activity.class.getSimpleName()));
        assertTrue(actual.contains("\n"));
        assertTrue(actual.contains("id"));
    }
}