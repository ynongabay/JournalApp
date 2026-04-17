package com.example.journalapp.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

// This class saves and loads entries from the phone using SharedPreferences
class JournalLocalDataSource(context: Context) {

    // SharedPreferences stores data as key-value pairs on the device
    private val prefs = context.applicationContext
        .getSharedPreferences("journalapp_prefs", Context.MODE_PRIVATE)

    private val gson = Gson()  // converts objects to/from JSON text

    // Get all saved entries, newest first
    fun getAllEntries(): List<JournalEntry> {
        val json = prefs.getString("entries", "[]") ?: "[]"
        val type = object : TypeToken<List<JournalEntry>>() {}.type
        val entries: List<JournalEntry> = gson.fromJson(json, type) ?: emptyList()
        return entries.sortedByDescending { it.createdAt }
    }

    // Save an entry (or update it if same ID exists)
    fun saveEntry(entry: JournalEntry) {
        val entries = getAllEntries().toMutableList()
        val index = entries.indexOfFirst { it.id == entry.id }
        if (index != -1) {
            entries[index] = entry  // update existing
        } else {
            entries.add(entry)      // add new
        }
        val json = gson.toJson(entries)
        prefs.edit().putString("entries", json).apply()
    }

    // Delete an entry by its ID
    fun deleteEntry(id: String) {
        val entries = getAllEntries().toMutableList()
        entries.removeAll { it.id == id }
        prefs.edit().putString("entries", gson.toJson(entries)).apply()
    }

    // Find one entry by its ID
    fun getEntryById(id: String): JournalEntry? {
        return getAllEntries().find { it.id == id }
    }

    // Search entries by title or content
    fun searchEntries(query: String): List<JournalEntry> {
        val lowerQuery = query.lowercase()
        return getAllEntries().filter {
            it.title.lowercase().contains(lowerQuery) ||
            it.content.lowercase().contains(lowerQuery)
        }
    }
}

