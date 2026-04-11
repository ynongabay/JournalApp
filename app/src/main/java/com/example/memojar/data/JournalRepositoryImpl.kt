package com.example.memojar.data

/**
 * JournalRepositoryImpl is the real implementation of JournalRepository.
 * It delegates all operations to JournalLocalDataSource.
 *
 * This class acts as a "middleman" between the ViewModel and the data source.
 * Right now it simply passes calls through, but in a larger app you could
 * add caching, combine multiple data sources (local + network), etc.
 */
class JournalRepositoryImpl(
    private val localDataSource: JournalLocalDataSource
) : JournalRepository {

    override fun getAllEntries(): List<JournalEntry> =
        localDataSource.getAllEntries()

    override fun saveEntry(entry: JournalEntry) =
        localDataSource.saveEntry(entry)

    override fun deleteEntry(id: String) =
        localDataSource.deleteEntry(id)

    override fun getEntryById(id: String): JournalEntry? =
        localDataSource.getEntryById(id)

    override fun searchEntries(query: String): List<JournalEntry> =
        localDataSource.searchEntries(query)
}

