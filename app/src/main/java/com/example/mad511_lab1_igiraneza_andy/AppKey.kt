package com.example.mad511_lab1_igiraneza_andy

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface AppKey : NavKey

@Serializable
data object ArtistList : AppKey

@Serializable
data object AddArtist : AppKey

@Serializable
data class ArtistDetail(val id: Int) : AppKey

@Serializable
data class ConfirmDelete(val id: Int) : AppKey

