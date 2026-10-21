package com.example.mad511_lab1_igiraneza_andy

import java.time.Instant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

interface ArtistRepository {

    val artists: Flow<List<Artist>>

    fun observeArtist(id: Int): Flow<Artist?>

    suspend fun addArtist(
        name: String,
        genre: String,
        yearFormed: Int
    )

    suspend fun deleteArtist(id: Int)
}

class InMemoryArtistRepository : ArtistRepository {

    private val _artists = MutableStateFlow<List<Artist>>(emptyList())

    override val artists: Flow<List<Artist>> =
        _artists.asStateFlow()

    private var nextId = 1

    override fun observeArtist(id: Int): Flow<Artist?> {
        return artists.map { artistList ->
            artistList.find { artist ->
                artist.id == id
            }
        }
    }

    override suspend fun addArtist(
        name: String,
        genre: String,
        yearFormed: Int
    ) {
        val artist = Artist(
            id = nextId,
            name = name,
            genre = genre,
            yearFormed = yearFormed,
            addedAt = Instant.now()
        )

        nextId++

        _artists.update { artistList ->
            artistList + artist
        }
    }

    override suspend fun deleteArtist(id: Int) {
        _artists.update { artistList ->
            artistList.filterNot { artist ->
                artist.id == id
            }
        }
    }
}
