import org.example.InMemoryDb;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class InMemoryDbTest {

    private InMemoryDb db;

    @Before
    public void setUp() {
        this.db = new InMemoryDb();
    }

    @Test
    public void testSetAndGet() {
        db.set("key1", "value1");
        assertEquals("value1", db.get("key1"));
    }

    @Test
    public void testGetNonExistentKey() {
        assertNull(db.get("unknownKey"));
    }

    @Test
    public void testSetUpdatesValue() {
        db.set("key1", "value1");
        db.set("key1", "value2");
        assertEquals("value2", db.get("key1"));
    }

    @Test
    public void testTransactionCommit() {
        db.begin();
        db.set("key1", "tempValue");
        assertNull(db.get("key1"));

        db.commit();
        assertEquals("tempValue", db.get("key1")); // Value should persist
    }

    @Test
    public void testTransactionRollback() {
        db.set("key1", "originalValue");

        db.begin();
        db.set("key1", "newValue");
        assertEquals("originalValue", db.get("key1"));

        db.rollback();
        assertEquals("originalValue", db.get("key1")); // Should revert to previous value
    }

    @Test
    public void testNestedTransactions() {
        db.set("key1", "initialValue");

        db.begin();
        db.set("key1", "firstTransaction");

        db.begin();
        db.set("key1", "secondTransaction");

        db.rollback();
        assertEquals("initialValue" , db.get("key1"));

        db.rollback();
        assertEquals("initialValue" , db.get("key1"));
    }

    @Test
    public void testCommitWithoutTransaction() {
        db.commit(); // No transactions started
        assertTrue(true); // Should not throw an exception
    }

    @Test
    public void testRollbackWithoutTransaction() {
        db.rollback(); // No transactions started
        assertTrue(true); // Should not throw an exception
    }

    @Test
    public void testGetCount() {
        db.set("key1", "value1");
        db.set("key1", "value2");
        assertEquals(2, db.getCount("key1")); // The count should reflect the updates
    }
}
