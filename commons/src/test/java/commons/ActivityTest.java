package commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActivityTest {
    @Test
    public void checkConstructor() {
        var a = new Activity("id", "path", "test", 40L, "source");
        assertEquals(a.getId(), "id");
        assertEquals(a.getImagePath(), "path");
        assertEquals(a.getTitle(), "test");
        assertEquals(a.getConsumptionInWh(), 40);
        assertEquals(a.getSource(), "source");
    }

    @Test
    public void equalsHashCode() {
        var a = new Activity("id", "path",  "test", 40L, "source");
        var b = new Activity("id", "path", "test", 40L, "source");
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void notEqualsHashCode() {
        var a = new Activity("id", "path", "test", 40L, "source");
        var b = new Activity("id", "path", "test", 30L, "source");
        assertNotEquals(a, b);
        assertNotEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void hasToString() {
        var actual = new Activity("id", "path", "test", 40L, "source").toString();
        assertTrue(actual.contains(Activity.class.getSimpleName()));
        assertTrue(actual.contains("\n"));
        assertTrue(actual.contains("id"));
    }
}