package com.example.mad511_lab1_igiraneza_andy

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.mad511_lab1_igiraneza_andy.ui.theme.MAD511_Lab1_Igiraneza_AndyTheme

@Composable
fun ConfirmDeleteRoute(
    viewModel: ConfirmDeleteViewModel,
    onConfirm: () -> Unit,
    onCancel: () -> Unit
) {
    ConfirmDeleteScreen(
        onConfirm = {
            viewModel.deleteArtist(onConfirm)
        },
        onCancel = onCancel
    )
}

@Composable
fun ConfirmDeleteScreen(
    onConfirm: () -> Unit,
    onCancel: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onCancel,
        title = {
            Text("Delete Artist")
        },
        text = {
            Text("Are you sure you want to delete this artist?")
        },
        confirmButton = {
            Button(
                onClick = onConfirm
            ) {
                Text("Delete")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onCancel
            ) {
                Text("Cancel")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun ConfirmDeleteScreenPreview() {
    MAD511_Lab1_Igiraneza_AndyTheme {
        ConfirmDeleteScreen(
            onConfirm = {},
            onCancel = {}
        )
    }
}