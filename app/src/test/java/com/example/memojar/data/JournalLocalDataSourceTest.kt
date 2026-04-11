package com.example.memojar.data

import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * JournalLocalDataSourceTest tests the data storage layer.
 *
 * It uses Robolectric to simulate Android's SharedPreferences on the JVM,
 * so we can run these tests without a real phone or emulator.
 *
 * Each test method follows the pattern:
 *   1. Arrange — set up the test data
 *   2. Act — call the method being tested
 *   3. Assert — verify the result is what we expect
 */
@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class JournalLocalDataSourceTest {

    private lateinit var dataSource: JournalLocalDataSource

    /**
     * Runs before EACH test. Creates a fresh data source and clears
     * any leftover data to ensure tests don't affect each other.
     */
    @Before
    fun setup() {
        dataSource = JournalLocalDataSource(ApplicationProvider.getApplicationContext())
        // Clear all saved entries so each test starts with a clean slate
        ApplicationProvider.getApplicationContext<android.content.Context>()
            .getSharedPreferences("memojar_prefs", android.content.Context.MODE_PRIVATE)
            .edit().clear().commit()
    }

    /**
     * Test: saving an entry and retrieving all entries.
     * Verifies that the entry we save can be loaded back.
     */
    @Test
    fun saveEntry_and_getAllEntries() {
        // Arrange: create a test entry
        val entry = JournalEntry(
            title = "Test",
            content = "Content",
            mood = "happy",
            tags = emptyList()
        )

        // Act: save it and then load all entries
        dataSource.saveEntry(entry)
        val entries = dataSource.getAllEntries()

        // Assert: we should have exactly 1 entry with the right title
        assertEquals(1, entries.size)
        assertEquals("Test", entries[0].title)
    }

    /**
     * Test: deleting an entry removes it from the list.
     */
    @Test
    fun deleteEntry_removesFromList() {
        // Arrange: save an entry
        val entry = JournalEntry(
            title = "To Delete",
            content = "Content",
            mood = "happy",
            tags = emptyList()
        )
        dataSource.saveEntry(entry)

        // Act: delete it
        dataSource.deleteEntry(entry.id)

        // Assert: the list should now be empty
        val entries = dataSource.getAllEntries()
        assertTrue(entries.isEmpty())
    }

    /**
     * Test: searching entries returns only matching results.
     */
    @Test
    fun searchEntries_returnsCorrectResults() {
        // Arrange: save two different entries
        val entry1 = JournalEntry(
            title = "Apple",
            content = "Fruit",
            mood = "happy",
            tags = emptyList()
        )
        val entry2 = JournalEntry(
            title = "Banana",
            content = "Yellow fruit",
            mood = "happy",
            tags = emptyList()
        )
        dataSource.saveEntry(entry1)
        dataSource.saveEntry(entry2)

        // Act: search for "apple"
        val results = dataSource.searchEntries("apple")

        // Assert: only the Apple entry should be returned
        assertEquals(1, results.size)
        assertEquals("Apple", results[0].title)
    }
}

