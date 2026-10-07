package com.example.mad511_lab1_igiraneza_andy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ConfirmDeleteViewModel(
    private val id: Int,
    private val repository: ArtistRepository
) : ViewModel() {

    fun deleteArtist(onDeleted: () -> Unit) {
        viewModelScope.launch {
            repository.deleteArtist(id)
            onDeleted()
        }
    }
}