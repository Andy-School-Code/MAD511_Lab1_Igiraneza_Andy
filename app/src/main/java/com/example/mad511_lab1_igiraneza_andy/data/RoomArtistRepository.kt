package com.example.mad511_lab1_igiraneza_andy.data

import com.example.mad511_lab1_igiraneza_andy.Artist
import com.example.mad511_lab1_igiraneza_andy.ArtistRepository
import java.time.Instant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomArtistRepository(
    private val dao: ArtistDao
) : ArtistRepository {

    override val artists: Flow<List<Artist>> =
        dao.observeAll().map { rows ->
            rows.map { it.toArtist() }
        }

    override fun observeArtist(id: Int): Flow<Artist?> =
        dao.observeById(id).map { it?.toArtist() }

    override suspend fun addArtist(
        name: String,
        genre: String,
        yearFormed: Int
    ) {
        dao.upsert(
            ArtistEntity(
                name = name,
                genre = genre,
                yearFormed = yearFormed,
                addedAt = Instant.now()
            )
        )
    }

    override suspend fun deleteArtist(id: Int) {
        dao.deleteById(id)
    }
}