package com.example.memojar.viewmodel

import com.example.memojar.data.JournalEntry
import com.example.memojar.data.JournalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

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

    override fun getEntryById(id: String): JournalEntry? = entries.find { it.id == id }

    override fun searchEntries(query: String): List<JournalEntry> = entries.filter {
        it.title.contains(query, true) || it.content.contains(query, true)
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
class JournalViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun deleteEntry_updatesStateFlow() = runTest {
        val fakeRepo = FakeJournalRepository()
        val entry = JournalEntry(title = "To Delete", content = "Test", mood = "neutral", tags = emptyList())
        fakeRepo.saveEntry(entry)

        val viewModel = JournalViewModel(fakeRepo)
        advanceUntilIdle() // let init block populate

        assertEquals(1, viewModel.entries.value.size)

        viewModel.deleteEntry(entry)
        advanceUntilIdle() // let scope refresh

        assertTrue(viewModel.entries.value.isEmpty())
    }
}
