package com.example.memojar.data

import java.util.UUID

data class JournalEntry(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val content: String,
    val mood: String,
    val tags: List<String>,
    val imagePath: String? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
