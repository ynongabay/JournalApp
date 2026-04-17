package com.example.journalapp.data

import java.util.UUID

// A data class that holds one journal entry
data class JournalEntry(
    val id: String = UUID.randomUUID().toString(),  // unique ID
    val title: String,
    val content: String,
    val mood: String,
    val tags: List<String>,
    val imageUri: String? = null,   // photo path, null if no photo
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)

