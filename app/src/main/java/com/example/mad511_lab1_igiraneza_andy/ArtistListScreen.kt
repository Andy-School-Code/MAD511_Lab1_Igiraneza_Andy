
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mad511_lab1_igiraneza_andy.ui.theme.MAD511_Lab1_Igiraneza_AndyTheme
import java.time.Instant

private val Burgundy = Color(0xFF5A0015)
private val Brown = Color(0xFF6F4E37)

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
        onAddClick = onAddClick,
        onQueryChange = viewModel::onQueryChange
    )
}

@Composable
fun ArtistListScreen(
    uiState: ArtistListUiState,
    onArtistClick: (Int) -> Unit,
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier,
    onQueryChange: (String) -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Burgundy)
            .padding(16.dp)
    ) {
        Text(
            text = "SetList",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = uiState.query,
            onValueChange = onQueryChange,
            label = { Text("Search artists") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedLabelColor = Color.White,
                unfocusedLabelColor = Color.White,
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.White,
                cursorColor = Color.White
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (uiState.artists.isEmpty()) {
            Text(
                text = if (uiState.hasArtists && uiState.query.isNotBlank()) {
                    "No matching artists"
                } else {
                    "No artists yet"
                },
                color = Color.White
            )
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(uiState.artists, key = { it.id }) { artist ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onArtistClick(artist.id) },
                        colors = CardDefaults.cardColors(
                            containerColor = Brown
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = if (artist.isFavourite) {
                                    "★ ${artist.name}"
                                } else {
                                    artist.name
                                },
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = artist.genre,
                                color = Color.White
                            )
                            Text(
                                text = "Formed: ${artist.yearFormed}",
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onAddClick,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Brown
            )
        ) {
            Text("Add Artist", color = Color.White)
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
                    Artist(1, "Metallica", "Metal", 1981, Instant.parse("2026-10-21T12:00:00Z")),
                    Artist(2, "Coldplay", "Alternative", 1997, Instant.parse("2026-10-21T12:00:00Z"))
                ),
                hasArtists = true
            ),
            onArtistClick = {},
            onAddClick = {}
        )
    }
}
