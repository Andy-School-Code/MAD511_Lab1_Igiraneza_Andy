package com.example.mad511_lab1_igiraneza_andy

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mad511_lab1_igiraneza_andy.ui.theme.MAD511_Lab1_Igiraneza_AndyTheme

@Composable
fun ArtistListRoute(
    viewModel: ArtistListViewModel,
    onArtistClick: (Int) -> Unit,
    onAddClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ArtistListScreen(
        uiState = uiState,
        onArtistClick = onArtistClick,
        onAddClick = onAddClick
    )
}

@Composable
fun ArtistListScreen(
    uiState: ArtistListUiState,
    onArtistClick: (Int) -> Unit,
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "SetList",
            style = MaterialTheme.typography.headlineMedium
        )

        Button(
            onClick = onAddClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Artist")
        }

        if (uiState.artists.isEmpty()) {
            Text("No artists added yet")
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    items = uiState.artists,
                    key = { artist -> artist.id }
                ) { artist ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onArtistClick(artist.id)
                            }
                            .padding(8.dp)
                    ) {
                        Text(
                            text = artist.name,
                            style = MaterialTheme.typography.titleMedium
                        )

                        Text(
                            text = "${artist.genre} • ${artist.yearFormed}"
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArtistListScreenPreview() {
    MAD511_Lab1_Igiraneza_AndyTheme {
        ArtistListScreen(
            uiState = ArtistListUiState(
                artists = listOf(
                    Artist(1, "Metallica", "Metal", 1981),
                    Artist(2, "Coldplay", "Alternative", 1997)
                )
            ),
            onArtistClick = {},
            onAddClick = {}
        )
    }
}