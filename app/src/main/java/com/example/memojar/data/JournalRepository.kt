package com.example.memojar.data

/**
 * JournalRepository is an interface that defines what operations
 * can be performed on journal entries.
 *
 * An "interface" is like a contract — it says WHAT methods must exist,
 * but not HOW they work. This is useful because:
 *   1. The ViewModel only depends on this interface, not the actual storage code.
 *   2. In tests, we can create a "fake" version that stores data in memory
 *      instead of using SharedPreferences.
 */
interface JournalRepository {

    /** Returns all journal entries, sorted by newest first. */
    fun getAllEntries(): List<JournalEntry>

    /** Saves a new entry or updates an existing one. */
    fun saveEntry(entry: JournalEntry)

    /** Deletes an entry by its unique ID. */
    fun deleteEntry(id: String)

    /** Finds a single entry by its ID. Returns null if not found. */
    fun getEntryById(id: String): JournalEntry?

    /** Searches entries by title or content. */
    fun searchEntries(query: String): List<JournalEntry>
}

