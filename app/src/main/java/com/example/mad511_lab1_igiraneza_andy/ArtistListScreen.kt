package com.example.mad511_lab1_igiraneza_andy

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
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
            .background(Color(0xFF5A0015))
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 32.dp,
                bottom = 16.dp
            ),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "SetList",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onAddClick,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF6F4E37),
                contentColor = Color.White
            )
        ) {
            Text("Add Artist")
        }

        if (uiState.artists.isEmpty()) {
            Text(
                text = "No artists added yet",
                color = Color.White
            )
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp)
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
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = artist.name,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )

                        Text(
                            text = "${artist.genre} • ${artist.yearFormed}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White
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
