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
import com.example.nhamngocduc.ui.home.HomeScreen
import com.example.nhamngocduc.ui.home.inner_screen.TopAlbumScreen
import com.example.nhamngocduc.ui.home.inner_screen.TopArtistScreen
import com.example.nhamngocduc.ui.home.inner_screen.TopTrackScreen
import com.example.nhamngocduc.ui.navigation.nav3.route.HomeRoute

@Composable
fun HomeNavGraph(
    modifier: Modifier = Modifier,
    toProfile: () -> Unit,
    toSettings: () -> Unit,
) {
    val backStack = rememberNavBackStack<HomeRoute>(HomeRoute.HomeMainRoute)

    NavDisplay(
        modifier = Modifier.fillMaxSize(),
        backStack = backStack,
        onBack = {
            backStack.removeLastOrNull()
        },
        entryDecorators = listOf(
            rememberSavedStateNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider {
            entry<HomeRoute.HomeMainRoute> {
                HomeScreen(
                    modifier = Modifier.fillMaxSize(),
                    navigateToAlbums = {
                        backStack.add(HomeRoute.TopAlbumsRoute(it))
                    },
                    navigateToTracks = {
                        backStack.add(HomeRoute.TopSongsRoute(it))
                    },
                    navigateToArtists = {
                        backStack.add(HomeRoute.TopArtistsRoute(it))
                    },
                    navigateToProfile = toProfile,
                    navigateToSettings = toSettings
                )
            }
            entry<HomeRoute.TopAlbumsRoute> {
                TopAlbumScreen(
                    modifier = Modifier.fillMaxSize(),
                    albums = it.albums,
                    onNavigateBack = {
                        backStack.removeLastOrNull()
                    },
                )
            }
            entry<HomeRoute.TopArtistsRoute> {
                TopArtistScreen(
                    modifier = Modifier.fillMaxSize(),
                    artists = it.artists,
                    onNavigateBack = {
                        backStack.removeLastOrNull()
                    }
                )
            }
            entry<HomeRoute.TopSongsRoute> {
                TopTrackScreen(
                    modifier = Modifier.fillMaxSize(),
                    tracks = it.tracks,
                    onNavigateBack = {
                        backStack.removeLastOrNull()
                    }
                )
            }
        }
    )
}