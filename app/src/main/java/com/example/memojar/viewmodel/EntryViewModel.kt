package com.example.memojar.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memojar.data.JournalEntry
import com.example.memojar.data.JournalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class EntryViewModel @Inject constructor(
    private val repository: JournalRepository
) : ViewModel() {

    var id = mutableStateOf("")
    var title = mutableStateOf("")
    var content = mutableStateOf("")
    var mood = mutableStateOf("neutral")
    var tags = mutableStateListOf<String>()
    var imagePath = mutableStateOf<String?>(null)
    var createdAt = mutableStateOf(0L)
    var updatedAt = mutableStateOf(0L)

    fun loadEntry(entryId: String) {
        viewModelScope.launch {
            val entry = repository.getEntryById(entryId)
            if (entry != null) {
                id.value = entry.id
                title.value = entry.title
                content.value = entry.content
                mood.value = entry.mood
                tags.clear()
                tags.addAll(entry.tags)
                imagePath.value = entry.imagePath
                createdAt.value = entry.createdAt
                updatedAt.value = entry.updatedAt
            }
        }
    }

    fun saveEntry() {
        viewModelScope.launch {
            val isNew = id.value.isBlank()
            val newId = if (isNew) UUID.randomUUID().toString() else id.value

            val existingEntry = if (!isNew) repository.getEntryById(newId) else null
            val createdAt = existingEntry?.createdAt ?: System.currentTimeMillis()

            val entry = JournalEntry(
                id = newId,
                title = title.value,
                content = content.value,
                mood = mood.value,
                tags = tags.toList(),
                imagePath = imagePath.value,
                createdAt = createdAt,
                updatedAt = System.currentTimeMillis()
            )

            repository.saveEntry(entry)
            clearState()
        }
    }

    fun deleteEntry(entryId: String) {
        viewModelScope.launch {
            repository.deleteEntry(entryId)
        }
    }

    private fun clearState() {
        id.value = ""
        title.value = ""
        content.value = ""
        mood.value = "neutral"
        tags.clear()
        imagePath.value = null
        createdAt.value = 0L
        updatedAt.value = 0L
    }
}
