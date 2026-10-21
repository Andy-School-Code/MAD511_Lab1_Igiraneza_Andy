package com.example.mad511_lab1_igiraneza_andy

import com.example.mad511_lab1_igiraneza_andy.ui.theme.MAD511_Lab1_Igiraneza_AndyTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

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
        onDelete = onDelete
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtistDetailScreen(
    uiState: ArtistDetailUiState,
    onBack: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Artist Details")
                },
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
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("Name: ${uiState.artist.name}")
                    Text("Genre: ${uiState.artist.genre}")
                    Text("Year Formed: ${uiState.artist.yearFormed}")
                    Text(
                        "Added: ${
                            uiState.artist.addedAt
                                .atZone(ZoneId.systemDefault())
                                .format(DateTimeFormatter.ofPattern("MMM d, yyyy h:mm a"))
                        }"
                    )

                    Button(
                        onClick = onDelete
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