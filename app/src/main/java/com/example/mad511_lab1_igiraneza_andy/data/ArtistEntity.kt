package com.example.mad511_lab1_igiraneza_andy.data

import androidx.room3.ColumnInfo
import androidx.room3.Entity
import androidx.room3.PrimaryKey
import com.example.mad511_lab1_igiraneza_andy.Artist
import java.time.Instant

@Entity(tableName = "artists")
data class ArtistEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "genre")
    val genre: String,

    @ColumnInfo(name = "year_formed")
    val yearFormed: Int,

    @ColumnInfo(name = "added_at")
    val addedAt: Instant,

    @ColumnInfo(name = "is_favourite", defaultValue = "0")
    val isFavourite: Boolean = false
)

fun ArtistEntity.toArtist(): Artist =
    Artist(
        id = id,
        name = name,
        genre = genre,
        yearFormed = yearFormed,
        addedAt = addedAt,
        isFavourite = isFavourite
    )

fun Artist.toEntity(): ArtistEntity =
    ArtistEntity(
        id = id,
        name = name,
        genre = genre,
        yearFormed = yearFormed,
        addedAt = addedAt,
        isFavourite = isFavourite
    )