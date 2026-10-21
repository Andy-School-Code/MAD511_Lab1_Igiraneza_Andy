package com.example.mad511_lab1_igiraneza_andy

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class AddArtistViewModelTest {

    @Test
    fun invalidYearDisablesAddAndValidYearEnablesAdd() {
        val repository: ArtistRepository =
            InMemoryArtistRepository()

        val viewModel = AddArtistViewModel(repository)

        viewModel.updateName("Metallica")
        viewModel.updateGenre("Metal")
        viewModel.updateYearFormed("1800")

        assertFalse(viewModel.uiState.value.isAddEnabled)

        viewModel.updateYearFormed("1981")

        assertTrue(viewModel.uiState.value.isAddEnabled)
    }
}