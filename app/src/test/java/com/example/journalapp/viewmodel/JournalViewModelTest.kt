package com.example.journalapp.viewmodel

import com.example.journalapp.data.JournalEntry
import com.example.journalapp.data.JournalRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

// Fake version of the repository — stores data in memory for testing
class FakeJournalRepository : JournalRepository {
    private val entries = mutableListOf<JournalEntry>()

    override fun getAllEntries(): List<JournalEntry> = entries.toList()

    override fun saveEntry(entry: JournalEntry) {
        val idx = entries.indexOfFirst { it.id == entry.id }
        if (idx >= 0) entries[idx] = entry else entries.add(entry)
    }

    override fun deleteEntry(id: String) { entries.removeAll { it.id == id } }

    override fun getEntryById(id: String): JournalEntry? = entries.find { it.id == id }

    override fun searchEntries(query: String): List<JournalEntry> =
        entries.filter { it.title.contains(query, true) || it.content.contains(query, true) }
}

// Tests for JournalViewModel
class JournalViewModelTest {

    // Test: deleting an entry removes it from the list
    @Test
    fun deleteEntry_removesFromList() {
        val fakeRepo = FakeJournalRepository()
        val entry = JournalEntry(title = "To Delete", content = "Test content",
            mood = "neutral", tags = emptyList())
        fakeRepo.saveEntry(entry)

        val viewModel = JournalViewModel(fakeRepo)
        assertEquals(1, viewModel.entries.size)

        viewModel.deleteEntry(entry)
        assertTrue(viewModel.entries.isEmpty())
    }
}

