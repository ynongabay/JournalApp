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
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: JournalRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val results: StateFlow<List<JournalEntry>> = _searchQuery
        .flatMapLatest { query ->
            flow {
                if (query.isBlank()) {
                    emit(emptyList())
                } else {
                    emit(repository.searchEntries(query))
                }
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun onQueryChange(q: String) {
        _searchQuery.value = q
    }

    fun deleteEntry(id: String) {
        repository.deleteEntry(id)
        val current = _searchQuery.value
        _searchQuery.value = ""
        _searchQuery.value = current
    }
}
