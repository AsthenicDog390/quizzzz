package commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    @Test
    public void testConstructor() {
        var p = new Player("id", "name", true);

        assertEquals("id", p.getId());
        assertEquals("name", p.getName());
        assertEquals("unknown", p.getGameId());
    }

    @Test
    public void testEquals() {
        var p1 = new Player("id", "name", true);
        var p2 = new Player("id", "name", true);

        assertEquals(p1, p2);
    }

    @Test
    public void testNotEquals() {
        var p1 = new Player("id", "name", true);
        var p2 = new Player("id", "George", true);

        assertNotEquals(p1, p2);
    }
}