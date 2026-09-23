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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Button
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
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

    SetListForm(
        name = name,
        onNameChange = { name = it },
        genre = genre,
        onGenreChange = { genre = it },
        yearFormed = yearFormed,
        onYearChange = { yearFormed = it },
        artists = artists,
        onAdd = {
            val year = yearFormed.toIntOrNull()

            if (year != null) {
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
    onAdd: () -> Unit,
    onDelete: (Artist) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(16.dp)
    ) {
        Text("SetList")

        OutlinedTextField(
            value = name,
            onValueChange = onNameChange,
            label = { Text("Artist Name") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = genre,
            onValueChange = onGenreChange,
            label = { Text("Genre") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = yearFormed,
            onValueChange = onYearChange,
            label = { Text("Year Formed") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Button(onClick = onAdd) {
            Text("Add Artist")
        }

        LazyColumn {
            items(artists) { artist ->
                Row {
                    Text(
                        "${artist.name} - ${artist.genre} - ${artist.yearFormed}"
                    )

                    Button(
                        onClick = {
                            onDelete(artist)
                        }
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
            onDelete = {}
        )
    }
}