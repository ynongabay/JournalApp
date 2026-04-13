package com.example.memojar.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.memojar.data.JournalEntry
import com.example.memojar.data.JournalRepository
import java.util.UUID

// Manages the form fields for creating or editing one entry
class EntryViewModel(
    private val repository: JournalRepository
) : ViewModel() {

    var id = mutableStateOf("")
    var title = mutableStateOf("")
    var content = mutableStateOf("")
    var mood = mutableStateOf("neutral")
    var tags = mutableStateListOf<String>()
    var imageUri = mutableStateOf<String?>(null)  // attached photo path
    var createdAt = mutableStateOf(0L)
    var updatedAt = mutableStateOf(0L)

    // Load an existing entry into the form
    fun loadEntry(entryId: String) {
        val entry = repository.getEntryById(entryId)
        if (entry != null) {
            id.value = entry.id
            title.value = entry.title
            content.value = entry.content
            mood.value = entry.mood
            tags.clear()
            tags.addAll(entry.tags)
            imageUri.value = entry.imageUri
            createdAt.value = entry.createdAt
            updatedAt.value = entry.updatedAt
        }
    }

    // Save the form as a new or updated entry
    fun saveEntry() {
        val isNew = id.value.isBlank()
        val entryId = if (isNew) UUID.randomUUID().toString() else id.value

        val existingEntry = if (!isNew) repository.getEntryById(entryId) else null
        val createTime = existingEntry?.createdAt ?: System.currentTimeMillis()

        val entry = JournalEntry(
            id = entryId,
            title = title.value,
            content = content.value,
            mood = mood.value,
            tags = tags.toList(),
            imageUri = imageUri.value,
            createdAt = createTime,
            updatedAt = System.currentTimeMillis()
        )

        repository.saveEntry(entry)
        clearForm()
    }

    fun deleteEntry(entryId: String) {
        repository.deleteEntry(entryId)
    }

    // Reset all fields
    private fun clearForm() {
        id.value = ""
        title.value = ""
        content.value = ""
        mood.value = "neutral"
        tags.clear()
        imageUri.value = null
        createdAt.value = 0L
        updatedAt.value = 0L
    }
}

