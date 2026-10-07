package com.example.mad511_lab1_igiraneza_andy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class AddArtistUiState(
    val name: String = "",
    val genre: String = "",
    val yearFormed: String = ""
) {
    val nameError: Boolean
        get() = name.isNotEmpty() && name.isBlank()

    val genreError: Boolean
        get() = genre.isNotEmpty() && genre.isBlank()

    val yearError: Boolean
        get() {
            val year = yearFormed.toIntOrNull()
            return yearFormed.isNotEmpty() &&
                    (year == null || year !in 1900..2026)
        }

    val isAddEnabled: Boolean
        get() {
            val year = yearFormed.toIntOrNull()

            return name.isNotBlank() &&
                    genre.isNotBlank() &&
                    year != null &&
                    year in 1900..2026
        }
}

class AddArtistViewModel(
    private val repository: ArtistRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddArtistUiState())
    val uiState: StateFlow<AddArtistUiState> = _uiState.asStateFlow()

    fun updateName(name: String) {
        _uiState.update { it.copy(name = name) }
    }

    fun updateGenre(genre: String) {
        _uiState.update { it.copy(genre = genre) }
    }

    fun updateYearFormed(yearFormed: String) {
        _uiState.update { it.copy(yearFormed = yearFormed) }
    }

    fun addArtist(onArtistAdded: () -> Unit) {
        val state = _uiState.value
        val year = state.yearFormed.toIntOrNull()

        if (!state.isAddEnabled || year == null) {
            return
        }

        viewModelScope.launch {
            repository.addArtist(
                name = state.name,
                genre = state.genre,
                yearFormed = year
            )
            onArtistAdded()
        }
    }
}