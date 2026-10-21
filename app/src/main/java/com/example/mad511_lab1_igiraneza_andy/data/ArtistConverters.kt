package com.example.mad511_lab1_igiraneza_andy.data

import androidx.room3.ColumnTypeConverter
import java.time.Instant

class ArtistConverters {
    @ColumnTypeConverter
    fun fromInstant(value: Instant?): Long? {
        return value?.toEpochMilli()
    }

    @ColumnTypeConverter
    fun toInstant(value: Long?): Instant? {
        return value?.let { Instant.ofEpochMilli(it) }
    }
}