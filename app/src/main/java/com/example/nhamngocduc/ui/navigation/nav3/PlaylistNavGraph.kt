package com.example.nhamngocduc.ui.navigation.nav3

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.example.nhamngocduc.ui.navigation.nav3.route.PlaylistRoute
import com.example.nhamngocduc.ui.playlist.playlist_detail.PlaylistDetailScreen
import com.example.nhamngocduc.ui.playlist.whole.PlaylistWholeScreen

@Composable
fun PlaylistNavGraph(
    modifier: Modifier = Modifier,
) {
    val backStack = rememberNavBackStack<PlaylistRoute>(PlaylistRoute.PlaylistWholeRoute)

    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        onBack = {
            backStack.removeLastOrNull()
        },
        entryDecorators = listOf(
            rememberSavedStateNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider {
            entry<PlaylistRoute.PlaylistWholeRoute> {
                PlaylistWholeScreen(
                    modifier = Modifier.fillMaxSize(),
                    navigateToPlaylistDetail = {
                        backStack.add(PlaylistRoute.PlaylistDetailRoute(it))
                    }
                )
            }
            entry<PlaylistRoute.PlaylistDetailRoute> {
                PlaylistDetailScreen(
                    modifier = Modifier.fillMaxSize(),
                    playlistId = it.playlistId,
                )
            }
        }
    )
}