package com.example.memojar.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class JournalLocalDataSource(context: Context) {
    private val prefs = context.applicationContext.getSharedPreferences("memojar_prefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun getAllEntries(): List<JournalEntry> {
        val json = prefs.getString("entries", "[]") ?: "[]"
        val type = object : TypeToken<List<JournalEntry>>() {}.type
        val entries: List<JournalEntry> = gson.fromJson(json, type) ?: emptyList()
        return entries.sortedByDescending { it.createdAt }
    }

    fun saveEntry(entry: JournalEntry) {
        val entries = getAllEntries().toMutableList()
        val index = entries.indexOfFirst { it.id == entry.id }
        if (index != -1) {
            entries[index] = entry
        } else {
            entries.add(entry)
        }
        val json = gson.toJson(entries)
        prefs.edit().putString("entries", json).apply()
    }

    fun deleteEntry(id: String) {
        val entries = getAllEntries().toMutableList()
        entries.removeAll { it.id == id }
        val json = gson.toJson(entries)
        prefs.edit().putString("entries", json).apply()
    }

    fun getEntryById(id: String): JournalEntry? {
        return getAllEntries().find { it.id == id }
    }

    fun searchEntries(query: String): List<JournalEntry> {
        val lowerQuery = query.lowercase()
        return getAllEntries().filter {
            it.title.lowercase().contains(lowerQuery) || 
            it.content.lowercase().contains(lowerQuery)
        }
    }

    fun getEntriesByCategory(category: String): List<JournalEntry> {
        return getAllEntries().filter { it.category == category }
    }
}
