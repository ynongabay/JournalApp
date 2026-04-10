package com.example.memojar.data

interface JournalRepository {
    fun getAllEntries(): List<JournalEntry>
    fun saveEntry(entry: JournalEntry)
    fun deleteEntry(id: String)
    fun getEntryById(id: String): JournalEntry?
    fun searchEntries(query: String): List<JournalEntry>
    fun getEntriesByCategory(category: String): List<JournalEntry>
}
