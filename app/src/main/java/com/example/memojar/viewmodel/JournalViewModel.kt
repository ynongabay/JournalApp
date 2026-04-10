package com.example.memojar.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memojar.data.JournalEntry
import com.example.memojar.data.JournalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@OptIn(ExperimentalCoroutinesApi::class)
class JournalViewModel @Inject constructor(
    private val repository: JournalRepository
) : ViewModel() {

    val selectedCategory = MutableStateFlow("day_to_day")
    private val refreshTrigger = MutableStateFlow(0)

    val entries: StateFlow<List<JournalEntry>> = combine(selectedCategory, refreshTrigger) { category, _ -> category }
        .flatMapLatest { category ->
            flow {
                emit(repository.getEntriesByCategory(category))
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    init {
        refresh()
    }

    /** Added this public refresh method so the UI can fetch immediately upon resuming without restarting the app. */
    fun refresh() {
        refreshTrigger.value += 1
    }

    fun selectCategory(category: String) {
        selectedCategory.value = category
    }

    fun deleteEntry(entry: JournalEntry) {
        viewModelScope.launch {
            repository.deleteEntry(entry.id)
            refresh()
        }
    }
}           
