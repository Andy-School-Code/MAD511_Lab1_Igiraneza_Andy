
package com.example.mad511_lab1_igiraneza_andy

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.scene.DialogSceneStrategy
import androidx.navigation3.ui.NavDisplay

@Composable
fun SetListNavigation() {
    val application =
        LocalContext.current.applicationContext as SetListApplication

    val repository = application.container.artistRepository
    val backStack = rememberNavBackStack(ArtistList)

    NavDisplay(
        backStack = backStack,

        onBack = {
            if (backStack.size > 1) {
                backStack.removeLastOrNull()
            }
        },

        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator<NavKey>(),
            rememberViewModelStoreNavEntryDecorator<NavKey>()
        ),

        sceneStrategies = listOf(
            remember { DialogSceneStrategy<NavKey>() }
        ),

        transitionSpec = {
            slideInHorizontally { it } togetherWith
                    slideOutHorizontally { -it }
        },

        popTransitionSpec = {
            slideInHorizontally { -it } togetherWith
                    slideOutHorizontally { it }
        },

        entryProvider = entryProvider {

            entry<ArtistList> {
                val vm: ArtistListViewModel = viewModel(
                    factory = viewModelFactory {
                        initializer {
                            ArtistListViewModel(repository)
                        }
                    }
                )

                ArtistListRoute(
                    viewModel = vm,
                    onArtistClick = { id ->
                        backStack.add(ArtistDetail(id))
                    },
                    onAddClick = {
                        backStack.add(AddArtist)
                    }
                )
            }

            entry<AddArtist> {
                val vm: AddArtistViewModel = viewModel(
                    factory = viewModelFactory {
                        initializer {
                            AddArtistViewModel(repository)
                        }
                    }
                )

                AddArtistRoute(
                    viewModel = vm,
                    onArtistAdded = {
                        backStack.removeLastOrNull()
                    }
                )
            }

            entry<ArtistDetail> { key ->
                val vm: ArtistDetailViewModel = viewModel(
                    factory = viewModelFactory {
                        initializer {
                            ArtistDetailViewModel(
                                key.id,
                                repository
                            )
                        }
                    }
                )

                ArtistDetailRoute(
                    viewModel = vm,
                    onBack = {
                        backStack.removeLastOrNull()
                    },
                    onDelete = {
                        backStack.add(ConfirmDelete(key.id))
                    }
                )
            }

            entry<ConfirmDelete>(
                metadata = DialogSceneStrategy.dialog()
            ) { key ->
                val vm: ConfirmDeleteViewModel = viewModel(
                    factory = viewModelFactory {
                        initializer {
                            ConfirmDeleteViewModel(
                                key.id,
                                repository
                            )
                        }
                    }
                )

                ConfirmDeleteRoute(
                    viewModel = vm,
                    onConfirm = {
                        while (backStack.size > 1) {
                            backStack.removeLastOrNull()
                        }
                    },
                    onCancel = {
                        backStack.removeLastOrNull()
                    }
                )
            }
        }
    )
}
