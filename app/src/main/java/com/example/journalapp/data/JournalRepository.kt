package com.example.journalapp.data

// Interface that lists all the things we can do with entries.
// We use an interface so we can swap in a fake version for testing.
interface JournalRepository {
    fun getAllEntries(): List<JournalEntry>
    fun saveEntry(entry: JournalEntry)
    fun deleteEntry(id: String)
    fun getEntryById(id: String): JournalEntry?
    fun searchEntries(query: String): List<JournalEntry>
}

