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
import androidx.compose.material3.TextField
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

    Column(
        modifier = modifier.padding(16.dp)
    ) {
        Text("SetList")

        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Artist Name") }
        )

        TextField(
            value = genre,
            onValueChange = { genre = it },
            label = { Text("Genre") }
        )

        TextField(
            value = yearFormed,
            onValueChange = { yearFormed = it },
            label = { Text("Year Formed") }
        )

        Button(onClick = {
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
        }) {
            Text("Add Artist")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SetListAppPreview() {
    MAD511_Lab1_Igiraneza_AndyTheme {
        SetListApp()
    }
}