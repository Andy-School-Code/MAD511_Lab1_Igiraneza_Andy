package com.example.mad511_lab1_igiraneza_andy.data

import androidx.room3.ColumnTypeConverters
import androidx.room3.Database
import androidx.room3.RoomDatabase

@Database(
    entities = [ArtistEntity::class],
    version = 1,
    exportSchema = true
)
@ColumnTypeConverters(ArtistConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun artistDao(): ArtistDao
}