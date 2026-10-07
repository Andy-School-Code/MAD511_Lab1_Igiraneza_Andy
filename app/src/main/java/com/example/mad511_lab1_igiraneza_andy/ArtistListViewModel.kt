package com.example.mad511_lab1_igiraneza_andy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

data class ArtistListUiState(
    val artists: List<Artist> = emptyList()
)

class ArtistListViewModel(
    repository: ArtistRepository
) : ViewModel() {

    val uiState: StateFlow<ArtistListUiState> =
        repository.artists
            .map { artists ->
                ArtistListUiState(artists = artists)
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = ArtistListUiState()
            )
}