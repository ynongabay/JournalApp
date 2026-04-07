package com.example.memojar.data

import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class JournalLocalDataSourceTest {

    private lateinit var dataSource: JournalLocalDataSource

    @Before
    fun setup() {
        dataSource = JournalLocalDataSource(ApplicationProvider.getApplicationContext())
        ApplicationProvider.getApplicationContext<android.content.Context>()
            .getSharedPreferences("memojar_prefs", android.content.Context.MODE_PRIVATE)
            .edit().clear().commit()
    }

    @Test
    fun saveEntry_and_getAllEntries() {
        val entry = JournalEntry(title = "Test", content = "Content", mood = "happy", tags = emptyList())
        dataSource.saveEntry(entry)

        val entries = dataSource.getAllEntries()
        assertEquals(1, entries.size)
        assertEquals("Test", entries[0].title)
    }

    @Test
    fun deleteEntry_removesFromList() {
        val entry = JournalEntry(title = "To Delete", content = "Content", mood = "happy", tags = emptyList())
        dataSource.saveEntry(entry)
        dataSource.deleteEntry(entry.id)

        val entries = dataSource.getAllEntries()
        assertTrue(entries.isEmpty())
    }

    @Test
    fun searchEntries_returnsCorrectResults() {
        val entry1 = JournalEntry(title = "Apple", content = "Fruit", mood = "happy", tags = emptyList())
        val entry2 = JournalEntry(title = "Banana", content = "Yellow fruit", mood = "happy", tags = emptyList())
        dataSource.saveEntry(entry1)
        dataSource.saveEntry(entry2)

        val results = dataSource.searchEntries("apple")
        assertEquals(1, results.size)
        assertEquals("Apple", results[0].title)
    }
}
