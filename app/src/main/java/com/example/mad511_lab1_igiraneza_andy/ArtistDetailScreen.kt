package com.example.mad511_lab1_igiraneza_andy

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mad511_lab1_igiraneza_andy.ui.theme.MAD511_Lab1_Igiraneza_AndyTheme
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

private val DetailBurgundy = Color(0xFF5A0015)
private val DetailBrown = Color(0xFF6F4E37)

@Composable
fun ArtistDetailRoute(
    viewModel: ArtistDetailViewModel,
    onBack: () -> Unit,
    onDelete: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ArtistDetailScreen(
        uiState = uiState,
        onBack = onBack,
        onDelete = onDelete,
        onToggleFavourite = viewModel::toggleFavourite
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtistDetailScreen(
    uiState: ArtistDetailUiState,
    onBack: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier,
    onToggleFavourite: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Artist Details") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Text("←")
                    }
                }
            )
        },
        modifier = modifier
    ) { innerPadding ->
        when (uiState) {
            is ArtistDetailUiState.Success -> {
                val formattedDate = DateTimeFormatter
                    .ofPattern("MMM d, yyyy h:mm a")
                    .withZone(ZoneId.systemDefault())
                    .format(uiState.artist.addedAt)

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(DetailBurgundy)
                        .padding(innerPadding)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("Name: ${uiState.artist.name}", color = Color.White)
                    Text("Genre: ${uiState.artist.genre}", color = Color.White)
                    Text("Year Formed: ${uiState.artist.yearFormed}", color = Color.White)
                    Text("Added: $formattedDate", color = Color.White)

                    Button(
                        onClick = onToggleFavourite,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = DetailBrown
                        )
                    ) {
                        Text(
                            if (uiState.artist.isFavourite) {
                                "★ Remove Favourite"
                            } else {
                                "☆ Add to Favourites"
                            }
                        )
                    }

                    Button(
                        onClick = onDelete,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = DetailBrown
                        )
                    ) {
                        Text("Delete")
                    }
                }
            }

            ArtistDetailUiState.NotFound -> {
                Text(
                    text = "Artist not found",
                    modifier = Modifier
                        .padding(innerPadding)
                        .padding(16.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArtistDetailScreenPreview() {
    MAD511_Lab1_Igiraneza_AndyTheme {
        ArtistDetailScreen(
            uiState = ArtistDetailUiState.Success(
                Artist(
                    id = 1,
                    name = "Metallica",
                    genre = "Metal",
                    yearFormed = 1981,
                    addedAt = Instant.parse("2026-10-21T12:00:00Z")
                )
            ),
            onBack = {},
            onDelete = {}
        )
    }
}
