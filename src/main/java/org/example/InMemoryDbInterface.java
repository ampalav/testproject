package org.example;

public interface InMemoryDbInterface {

    /**
     * Retrieves the value associated with the given key.
     * @param key the key to look up
     * @return the value associated with the key, or null if not found
     */
    String get(String key);

    /**
     * Retrieves the count of updates made to the given key.
     * @param key the key to check
     * @return the count of updates for the key
     */
    int getCount(String key);

    /**
     * Sets a key-value pair in the database.
     * @param key the key to set
     * @param value the value to associate with the key
     */
    void set(String key, String value);

    /**
     * Begins a new transaction.
     */
    void begin();

    /**
     * Commits the current transaction, persisting changes to the database.
     */
    void commit();

    /**
     * Rolls back the current transaction, discarding any uncommitted changes.
     */
    void rollback();
}

