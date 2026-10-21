package com.example.mad511_lab1_igiraneza_andy.data

import androidx.room3.Dao
import androidx.room3.Query
import androidx.room3.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ArtistDao {
    @Query("SELECT * FROM artists ORDER BY name COLLATE NOCASE ASC")
    fun observeAll(): Flow<List<ArtistEntity>>

    @Query("SELECT * FROM artists WHERE id = :id LIMIT 1")
    fun observeById(id: Int): Flow<ArtistEntity?>

    @Query(
        "SELECT * FROM artists WHERE name LIKE :prefix || '%' " +
                "COLLATE NOCASE ORDER BY name COLLATE NOCASE ASC"
    )
    fun searchByPrefix(prefix: String): Flow<List<ArtistEntity>>

    @Query("SELECT * FROM artists WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): ArtistEntity?

    @Upsert
    suspend fun upsert(artist: ArtistEntity)

    @Query("DELETE FROM artists WHERE id = :id")
    suspend fun deleteById(id: Int)
}