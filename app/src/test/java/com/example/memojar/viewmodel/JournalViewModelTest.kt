package com.example.memojar.viewmodel

import com.example.memojar.data.JournalEntry
import com.example.memojar.data.JournalRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * FakeJournalRepository is a test-only version of JournalRepository.
 * Instead of using SharedPreferences (which needs Android),
 * it stores entries in a simple in-memory list.
 *
 * This is WHY we use an interface (JournalRepository) — so we can
 * swap the real implementation for this fake one during testing.
 */
class FakeJournalRepository : JournalRepository {
    private val entries = mutableListOf<JournalEntry>()

    override fun getAllEntries(): List<JournalEntry> = entries.toList()

    override fun saveEntry(entry: JournalEntry) {
        val idx = entries.indexOfFirst { it.id == entry.id }
        if (idx >= 0) entries[idx] = entry else entries.add(entry)
    }

    override fun deleteEntry(id: String) {
        entries.removeAll { it.id == id }
    }

    override fun getEntryById(id: String): JournalEntry? =
        entries.find { it.id == id }

    override fun searchEntries(query: String): List<JournalEntry> =
        entries.filter {
            it.title.contains(query, true) || it.content.contains(query, true)
        }
}

/**
 * JournalViewModelTest tests the JournalViewModel logic.
 *
 * It uses FakeJournalRepository instead of the real one,
 * so we can test the ViewModel without needing Android or a database.
 */
class JournalViewModelTest {

    /**
     * Test: deleting an entry through the ViewModel updates the list.
     *
     * Steps:
     *   1. Create a fake repo with one entry
     *   2. Create a ViewModel with that repo
     *   3. Verify the entry is in the list
     *   4. Delete the entry
     *   5. Verify the list is now empty
     */
    @Test
    fun deleteEntry_removesFromList() {
        // Arrange: create a fake repo and add one entry
        val fakeRepo = FakeJournalRepository()
        val entry = JournalEntry(
            title = "To Delete",
            content = "Test content",
            mood = "neutral",
            tags = emptyList()
        )
        fakeRepo.saveEntry(entry)

        // Act: create the ViewModel (it loads entries in init)
        val viewModel = JournalViewModel(fakeRepo)

        // Assert: the entry should be in the list
        assertEquals(1, viewModel.entries.size)

        // Act: delete the entry
        viewModel.deleteEntry(entry)

        // Assert: the list should now be empty
        assertTrue(viewModel.entries.isEmpty())
    }
}

