package com.example.mad511_lab1_igiraneza_andy.data

import android.content.Context
import androidx.room3.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.time.Instant

@RunWith(AndroidJUnit4::class)
class ArtistDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var dao: ArtistDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        database = Room.inMemoryDatabaseBuilder<AppDatabase>(context)
            .setDriver(BundledSQLiteDriver())
            .build()

        dao = database.artistDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    private fun artist(
        name: String,
        id: Int = 0,
        favourite: Boolean = false
    ): ArtistEntity {
        return ArtistEntity(
            id = id,
            name = name,
            genre = "Rock",
            yearFormed = 2000,
            addedAt = Instant.parse("2026-10-21T12:00:00Z"),
            isFavourite = favourite
        )
    }

    @Test
    fun artistsAreSortedByName() = runBlocking {
        dao.upsert(artist("Metallica"))
        dao.upsert(artist("Coldplay"))
        dao.upsert(artist("ABBA"))

        val names = dao.observeAll().first().map { it.name }

        Assert.assertEquals(listOf("ABBA", "Coldplay", "Metallica"), names)
    }

    @Test
    fun prefixSearchIsCaseInsensitive() = runBlocking {
        dao.upsert(artist("Metallica"))
        dao.upsert(artist("Megadeth"))
        dao.upsert(artist("Coldplay"))

        val matches = dao.searchByPrefix("me").first()
        val names = matches.map { it.name }

        Assert.assertEquals(2, names.size)
        Assert.assertTrue(names.contains("Metallica"))
        Assert.assertTrue(names.contains("Megadeth"))

        val uppercaseMatches = dao.searchByPrefix("ME").first()
        Assert.assertEquals(2, uppercaseMatches.size)

        val nonPrefixMatches = dao.searchByPrefix("tal").first()
        Assert.assertTrue(nonPrefixMatches.isEmpty())
    }

    @Test
    fun upsertUpdatesExistingArtist() = runBlocking {
        dao.upsert(artist("Metallica"))

        val existing = dao.observeAll().first().single()

        dao.upsert(
            existing.copy(
                name = "Metallica Updated",
                isFavourite = true
            )
        )

        val artists = dao.observeAll().first()

        Assert.assertEquals(1, artists.size)
        Assert.assertEquals("Metallica Updated", artists.single().name)
        Assert.assertTrue(artists.single().isFavourite)
    }

    @Test
    fun deleteRemovesArtist() = runBlocking {
        dao.upsert(artist("Metallica"))

        val existing = dao.observeAll().first().single()

        dao.deleteById(existing.id)

        Assert.assertTrue(dao.observeAll().first().isEmpty())
        Assert.assertFalse(dao.observeAll().first().any { it.id == existing.id })
    }
}