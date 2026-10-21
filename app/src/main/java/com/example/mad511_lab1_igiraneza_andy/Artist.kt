package com.example.mad511_lab1_igiraneza_andy
import java.time.Instant

data class Artist(
    val id: Int,
    val name: String,
    val genre: String,
    val yearFormed: Int,
    val addedAt: Instant
)