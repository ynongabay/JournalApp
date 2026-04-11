package com.example.memojar.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.memojar.data.JournalEntry
import com.example.memojar.data.JournalRepository
import java.util.UUID

/**
 * EntryViewModel manages the state for creating, editing, and viewing
 * a single journal entry.
 *
 * Each field (title, content, mood, etc.) is stored as a separate
 * mutableStateOf value. When any of these values change, the Compose
 * UI automatically updates to show the new value.
 */
class EntryViewModel(
    private val repository: JournalRepository
) : ViewModel() {

    // The unique ID of the entry being edited (empty string for new entries)
    var id = mutableStateOf("")

    // The title text field
    var title = mutableStateOf("")

    // The content/body text field
    var content = mutableStateOf("")

    // The selected mood (defaults to "neutral")
    var mood = mutableStateOf("neutral")

    // The list of tags added to this entry
    // mutableStateListOf works like a regular list but triggers UI updates
    var tags = mutableStateListOf<String>()

    // Timestamps for when the entry was created and last updated
    var createdAt = mutableStateOf(0L)
    var updatedAt = mutableStateOf(0L)

    /**
     * Loads an existing entry's data into the form fields.
     * Called when the user wants to view or edit an entry.
     */
    fun loadEntry(entryId: String) {
        val entry = repository.getEntryById(entryId)
        if (entry != null) {
            id.value = entry.id
            title.value = entry.title
            content.value = entry.content
            mood.value = entry.mood
            tags.clear()
            tags.addAll(entry.tags)
            createdAt.value = entry.createdAt
            updatedAt.value = entry.updatedAt
        }
    }

    /**
     * Saves the current form data as a journal entry.
     * If the ID is blank, creates a new entry with a new ID.
     * If the ID exists, updates the existing entry.
     */
    fun saveEntry() {
        // Determine if this is a new entry or an update
        val isNew = id.value.isBlank()
        val entryId = if (isNew) UUID.randomUUID().toString() else id.value

        // For existing entries, keep the original creation time
        val existingEntry = if (!isNew) repository.getEntryById(entryId) else null
        val createTime = existingEntry?.createdAt ?: System.currentTimeMillis()

        // Build the entry object from the form fields
        val entry = JournalEntry(
            id = entryId,
            title = title.value,
            content = content.value,
            mood = mood.value,
            tags = tags.toList(),
            createdAt = createTime,
            updatedAt = System.currentTimeMillis()
        )

        // Save to the repository
        repository.saveEntry(entry)

        // Clear the form after saving
        clearForm()
    }

    /**
     * Deletes an entry by its ID.
     */
    fun deleteEntry(entryId: String) {
        repository.deleteEntry(entryId)
    }

    /**
     * Clears all form fields. Called after saving to reset the form.
     */
    private fun clearForm() {
        id.value = ""
        title.value = ""
        content.value = ""
        mood.value = "neutral"
        tags.clear()
        createdAt.value = 0L
        updatedAt.value = 0L
    }
}

