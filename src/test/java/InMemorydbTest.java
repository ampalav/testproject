import org.example.InMemoryDb;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class InMemorydbTest {

    @Test
    public void testBasicOperations() {
        InMemoryDb db = new InMemoryDb();

        db.set("a", "apple");

        assertEquals("apple", db.get("a"));

        db.set("b", "banana");
        assertEquals("banana", db.get("b"));

        assertEquals(1, db.getCount("apple"));
        assertEquals(1, db.getCount("banana"));

        db.set("a", "banana"); // Overwrite
        assertEquals("banana", db.get("a"));
        assertEquals(0, db.getCount("apple"));
        assertEquals(2, db.getCount("banana"));
    }
}
