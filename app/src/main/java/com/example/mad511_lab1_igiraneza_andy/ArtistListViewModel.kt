package com.example.mad511_lab1_igiraneza_andy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn

data class ArtistListUiState(
    val artists: List<Artist> = emptyList(),
    val query: String = "",
    val hasArtists: Boolean = false
)

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class ArtistListViewModel(
    repository: ArtistRepository
) : ViewModel() {

    private val query = MutableStateFlow("")

    fun onQueryChange(value: String) {
        query.value = value
    }

    private val results = query
        .debounce(300)
        .distinctUntilChanged()
        .flatMapLatest { term ->
            repository.search(term)
        }

    val uiState: StateFlow<ArtistListUiState> =
        combine(
            query,
            results,
            repository.artists
        ) { currentQuery, matchingArtists, allArtists ->
            ArtistListUiState(
                artists = matchingArtists,
                query = currentQuery,
                hasArtists = allArtists.isNotEmpty()
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ArtistListUiState()
        )
}
