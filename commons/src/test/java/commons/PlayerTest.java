package commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    @Test
    public void testConstructor() {
        var p = new Player("id", "name");

        assertEquals("id", p.getId());
        assertEquals("name", p.getName());
    }

    @Test
    public void testEquals() {
        var p1 = new Player("id", "name");
        var p2 = new Player("id", "name");

        assertEquals(p1, p2);
    }

    @Test
    public void testNotEquals() {
        var p1 = new Player("id", "name");
        var p2 = new Player("id", "George");

        assertNotEquals(p1, p2);
    }
}