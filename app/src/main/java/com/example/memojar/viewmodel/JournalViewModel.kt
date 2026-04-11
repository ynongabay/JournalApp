package com.example.memojar.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.memojar.data.JournalEntry
import com.example.memojar.data.JournalRepository

/**
 * JournalViewModel manages the list of journal entries displayed on the home screen.
 *
 * A ViewModel survives screen rotations and configuration changes,
 * so the data is not lost when the user rotates their phone.
 *
 * "mutableStateOf" makes the entries list reactive — when the list changes,
 * any Compose UI reading it will automatically update on screen.
 */
class JournalViewModel(
    private val repository: JournalRepository
) : ViewModel() {

    // The list of all journal entries.
    // "by mutableStateOf(...)" makes this a reactive property:
    // whenever we assign a new value, the UI automatically recomposes.
    // "private set" means only this ViewModel can change the list.
    var entries by mutableStateOf<List<JournalEntry>>(emptyList())
        private set

    // Load entries when the ViewModel is first created
    init {
        loadEntries()
    }

    /**
     * Loads all entries from the repository and updates the list.
     */
    fun loadEntries() {
        entries = repository.getAllEntries()
    }

    /**
     * Deletes an entry and refreshes the list.
     */
    fun deleteEntry(entry: JournalEntry) {
        repository.deleteEntry(entry.id)
        loadEntries()  // Reload the list after deleting
    }

    /**
     * Reloads entries from the repository.
     * Called when returning to the screen to show any changes.
     */
    fun refresh() {
        loadEntries()
    }
}
