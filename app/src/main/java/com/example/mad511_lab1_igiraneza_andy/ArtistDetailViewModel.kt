package com.example.mad511_lab1_igiraneza_andy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

sealed interface ArtistDetailUiState {
    data class Success(val artist: Artist) : ArtistDetailUiState
    data object NotFound : ArtistDetailUiState
}

class ArtistDetailViewModel(
    id: Int,
    repository: ArtistRepository
) : ViewModel() {

    val uiState: StateFlow<ArtistDetailUiState> =
        repository.observeArtist(id)
            .map { artist ->
                if (artist != null) {
                    ArtistDetailUiState.Success(artist)
                } else {
                    ArtistDetailUiState.NotFound
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = ArtistDetailUiState.NotFound
            )
}