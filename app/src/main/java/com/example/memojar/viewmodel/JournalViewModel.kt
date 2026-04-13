package com.example.memojar.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.memojar.data.JournalEntry
import com.example.memojar.data.JournalRepository

// Manages the list of entries shown on the home screen
class JournalViewModel(
    private val repository: JournalRepository
) : ViewModel() {

    // The list of entries. UI updates automatically when this changes.
    var entries by mutableStateOf<List<JournalEntry>>(emptyList())
        private set

    init { loadEntries() }  // load entries when created

    fun loadEntries() {
        entries = repository.getAllEntries()
    }

    fun deleteEntry(entry: JournalEntry) {
        repository.deleteEntry(entry.id)
        loadEntries()
    }

    fun refresh() { loadEntries() }
}

