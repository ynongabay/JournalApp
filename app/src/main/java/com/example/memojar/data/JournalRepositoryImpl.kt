package com.example.memojar.data

// Real implementation of JournalRepository.
// It just passes each call to the local data source.
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

