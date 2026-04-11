package com.example.memojar.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * JournalLocalDataSource handles saving and loading journal entries
 * from the device's local storage using SharedPreferences.
 *
 * SharedPreferences is Android's simple key-value storage system.
 * We store all entries as a single JSON string.
 */
class JournalLocalDataSource(context: Context) {

    // Get a reference to SharedPreferences using the app's context
    private val prefs = context.applicationContext
        .getSharedPreferences("memojar_prefs", Context.MODE_PRIVATE)

    // Gson is a library that converts Kotlin objects to/from JSON strings
    private val gson = Gson()

    /**
     * Returns all journal entries, sorted by newest first.
     */
    fun getAllEntries(): List<JournalEntry> {
        // Read the JSON string from SharedPreferences (defaults to "[]" if empty)
        val json = prefs.getString("entries", "[]") ?: "[]"

        // Convert the JSON string back into a List of JournalEntry objects.
        // TypeToken is needed because of how Java/Kotlin handles generic types.
        val type = object : TypeToken<List<JournalEntry>>() {}.type
        val entries: List<JournalEntry> = gson.fromJson(json, type) ?: emptyList()

        // Sort entries so the newest ones appear first
        return entries.sortedByDescending { it.createdAt }
    }

    /**
     * Saves a journal entry. If an entry with the same ID already exists,
     * it updates that entry. Otherwise, it adds a new entry.
     */
    fun saveEntry(entry: JournalEntry) {
        // Get the current list of entries
        val entries = getAllEntries().toMutableList()

        // Check if an entry with this ID already exists
        val index = entries.indexOfFirst { it.id == entry.id }
        if (index != -1) {
            // Update the existing entry
            entries[index] = entry
        } else {
            // Add as a new entry
            entries.add(entry)
        }

        // Convert the updated list back to JSON and save it
        val json = gson.toJson(entries)
        prefs.edit().putString("entries", json).apply()
    }

    /**
     * Deletes a journal entry by its ID.
     */
    fun deleteEntry(id: String) {
        val entries = getAllEntries().toMutableList()
        // Remove all entries that match the given ID
        entries.removeAll { it.id == id }
        // Save the updated list
        val json = gson.toJson(entries)
        prefs.edit().putString("entries", json).apply()
    }

    /**
     * Finds and returns a single entry by its ID.
     * Returns null if no entry with that ID exists.
     */
    fun getEntryById(id: String): JournalEntry? {
        return getAllEntries().find { it.id == id }
    }

    /**
     * Searches entries by title or content.
     * Returns entries where the title or content contains the search query.
     */
    fun searchEntries(query: String): List<JournalEntry> {
        val lowerQuery = query.lowercase()
        return getAllEntries().filter {
            it.title.lowercase().contains(lowerQuery) ||
            it.content.lowercase().contains(lowerQuery)
        }
    }
}

