package com.example.mad511_lab1_igiraneza_andy

import android.content.Context
import com.example.mad511_lab1_igiraneza_andy.data.AppDatabase
import com.example.mad511_lab1_igiraneza_andy.data.RoomArtistRepository

class AppContainer(context: Context) {
    val artistRepository: ArtistRepository =
        RoomArtistRepository(
            AppDatabase.get(context).artistDao()
        )
}