package com.example.memojar.data

import java.util.UUID

/**
 * JournalEntry represents a single journal entry in the app.
 *
 * A "data class" in Kotlin automatically generates useful methods like
 * equals(), hashCode(), toString(), and copy() based on the properties
 * defined in the constructor.
 */
data class JournalEntry(
    // A unique ID for each entry, generated automatically using UUID
    val id: String = UUID.randomUUID().toString(),

    // The title of the journal entry
    val title: String,

    // The main text content of the entry
    val content: String,

    // The mood associated with the entry (e.g., "happy", "sad", "neutral")
    val mood: String,

    // A list of tags the user can add to organize entries
    val tags: List<String>,

    // The URI (path) to a photo the user attached, or null if no photo
    val imageUri: String? = null,

    // The timestamp when the entry was first created (milliseconds since 1970)
    val createdAt: Long = System.currentTimeMillis(),

    // The timestamp when the entry was last updated
    val updatedAt: Long = System.currentTimeMillis()
)


