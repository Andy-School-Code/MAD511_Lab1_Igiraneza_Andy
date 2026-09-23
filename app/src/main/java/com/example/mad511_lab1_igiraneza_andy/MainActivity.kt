package com.example.mad511_lab1_igiraneza_andy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Button
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.derivedStateOf
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.Alignment
import androidx.compose.foundation.background
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.graphics.Color
import com.example.mad511_lab1_igiraneza_andy.ui.theme.MAD511_Lab1_Igiraneza_AndyTheme

data class Artist(
    val name: String,
    val genre: String,
    val yearFormed: Int
)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MAD511_Lab1_Igiraneza_AndyTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SetListApp(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun SetListApp(modifier: Modifier = Modifier) {
    var name by remember { mutableStateOf("") }
    var genre by remember { mutableStateOf("") }
    var yearFormed by remember { mutableStateOf("") }

    val artists = remember { mutableStateListOf<Artist>() }

    val isFormValid by remember {
        derivedStateOf {
            val year = yearFormed.toIntOrNull()

            name.isNotBlank() &&
                    genre.isNotBlank() &&
                    year != null &&
                    year in 1900..2026
        }
    }

    SetListForm(
        name = name,
        onNameChange = { name = it },
        genre = genre,
        onGenreChange = { genre = it },
        yearFormed = yearFormed,
        onYearChange = { yearFormed = it },
        artists = artists,
        addEnabled = isFormValid,
        onAdd = {
            val year = yearFormed.toIntOrNull()

            if (year != null && year in 1900..2026) {
                artists.add(
                    Artist(
                        name = name,
                        genre = genre,
                        yearFormed = year
                    )
                )

                name = ""
                genre = ""
                yearFormed = ""
            }
        },
        onDelete = { artist ->
            artists.remove(artist)
        },
        modifier = modifier
    )
}
@Composable
fun SetListForm(
    name: String,
    onNameChange: (String) -> Unit,
    genre: String,
    onGenreChange: (String) -> Unit,
    yearFormed: String,
    onYearChange: (String) -> Unit,
    artists: List<Artist>,
    addEnabled: Boolean,
    onAdd: () -> Unit,
    onDelete: (Artist) -> Unit,
    modifier: Modifier = Modifier
) {
    val nameError = name.isNotEmpty() && name.isBlank()
    val genreError = genre.isNotEmpty() && genre.isBlank()

    val yearNumber = yearFormed.toIntOrNull()
    val yearError = yearFormed.isNotEmpty() &&
            (yearNumber == null || yearNumber !in 1900..2026)

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF5A0015))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "SetList",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Text(
            text = "Add an artist to your music library",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White
        )

        OutlinedTextField(
            value = name,
            onValueChange = onNameChange,
            label = { Text("Artist Name") },
            isError = nameError,
            supportingText = {
                if (nameError) {
                    Text("Artist name is required")
                }
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedLabelColor = Color.White,
                unfocusedLabelColor = Color.White,
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.White
            )
        )

        OutlinedTextField(
            value = genre,
            onValueChange = onGenreChange,
            label = { Text("Genre") },
            isError = genreError,
            supportingText = {
                if (genreError) {
                    Text("Genre is required")
                }
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedLabelColor = Color.White,
                unfocusedLabelColor = Color.White,
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.White
            )
        )

        OutlinedTextField(
            value = yearFormed,
            onValueChange = onYearChange,
            label = { Text("Year Formed") },
            isError = yearError,
            supportingText = {
                if (yearError) {
                    Text("Enter a year between 1900 and 2026")
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedLabelColor = Color.White,
                unfocusedLabelColor = Color.White,
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.White
            )
        )

        Button(
            onClick = onAdd,
            enabled = addEnabled,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF6F4E37),
                contentColor = Color.White
            )
        ) {
            Text("Add Artist")
        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(artists) { artist ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = artist.name,
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White
                        )

                        Text(
                            text = "${artist.genre} • ${artist.yearFormed}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White
                        )
                    }

                    Button(
                        onClick = {
                            onDelete(artist)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF6F4E37),
                            contentColor = Color.White
                        )
                    ) {
                        Text("Delete")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SetListFormPreview() {
    MAD511_Lab1_Igiraneza_AndyTheme {
        SetListForm(
            name = "Metallica",
            onNameChange = {},
            genre = "Metal",
            onGenreChange = {},
            yearFormed = "1981",
            onYearChange = {},
            artists = listOf(
                Artist("Metallica", "Metal", 1981),
                Artist("Coldplay", "Alternative", 1997),
                Artist("Drake", "Hip Hop", 2001)
            ),
            onAdd = {},
            addEnabled = true,
            onDelete = {}
        )
    }
}