package com.example.mad511_lab1_igiraneza_andy

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mad511_lab1_igiraneza_andy.ui.theme.MAD511_Lab1_Igiraneza_AndyTheme

@Composable
fun AddArtistRoute(
    viewModel: AddArtistViewModel,
    onArtistAdded: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    AddArtistScreen(
        uiState = uiState,
        onNameChange = viewModel::updateName,
        onGenreChange = viewModel::updateGenre,
        onYearChange = viewModel::updateYearFormed,
        onAdd = {
            viewModel.addArtist()
            onArtistAdded()
        }
    )
}

@Composable
fun AddArtistScreen(
    uiState: AddArtistUiState,
    onNameChange: (String) -> Unit,
    onGenreChange: (String) -> Unit,
    onYearChange: (String) -> Unit,
    onAdd: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedTextField(
            value = uiState.name,
            onValueChange = onNameChange,
            label = { Text("Artist Name") },
            isError = uiState.nameError,
            supportingText = {
                if (uiState.nameError) {
                    Text("Artist name is required")
                }
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = uiState.genre,
            onValueChange = onGenreChange,
            label = { Text("Genre") },
            isError = uiState.genreError,
            supportingText = {
                if (uiState.genreError) {
                    Text("Genre is required")
                }
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = uiState.yearFormed,
            onValueChange = onYearChange,
            label = { Text("Year Formed") },
            isError = uiState.yearError,
            supportingText = {
                if (uiState.yearError) {
                    Text("Enter a year between 1900 and 2026")
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = onAdd,
            enabled = uiState.isAddEnabled,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Artist")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddArtistScreenPreview() {
    MAD511_Lab1_Igiraneza_AndyTheme {
        AddArtistScreen(
            uiState = AddArtistUiState(
                name = "Metallica",
                genre = "Metal",
                yearFormed = "1981"
            ),
            onNameChange = {},
            onGenreChange = {},
            onYearChange = {},
            onAdd = {}
        )
    }
}