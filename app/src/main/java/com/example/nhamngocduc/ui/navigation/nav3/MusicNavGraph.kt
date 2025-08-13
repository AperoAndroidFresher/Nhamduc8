package com.example.nhamngocduc.ui.navigation.nav3

import android.content.Context
import android.content.Intent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.example.nhamngocduc.service.MusicPlayerService
import com.example.nhamngocduc.ui.now_playing.NowPlayingBar
import com.example.nhamngocduc.ui.components.animation.scaleOnPress
import com.example.nhamngocduc.ui.library.LibraryScreen
import com.example.nhamngocduc.ui.navigation.nav3.route.BottomBarRoute
import com.example.nhamngocduc.ui.navigation.nav3.route.bottomBarItems

@Composable
fun MusicNavGraph(
    modifier: Modifier = Modifier,
    toProfile: () -> Unit,
) {
    val backStack = rememberNavBackStack<BottomBarRoute>(BottomBarRoute.HomeRoute)

    var currentBottomBarRoute: BottomBarRoute = backStack.lastOrNull() as? BottomBarRoute ?: BottomBarRoute.HomeRoute

    Scaffold(
        modifier = modifier,
        bottomBar = {
            BottomMusicBar(
                modifier = Modifier.fillMaxWidth(),
                currentBottomBarRoute = currentBottomBarRoute,
                backStack = backStack,
                add = { newScreen ->
                    backStack.add(newScreen)
                }
            )
        }
    ) { paddingValues ->
        val paddingTop = paddingValues.calculateTopPadding()
        val paddingLeft = paddingValues.calculateStartPadding(LayoutDirection.Ltr)
        val paddingRight = paddingValues.calculateEndPadding(LayoutDirection.Ltr)

        NavDisplay(
            modifier = Modifier.padding(top = paddingTop, start = paddingLeft, end = paddingRight),
            backStack = backStack,
            onBack = {
                backStack.removeLastOrNull()
                val element = backStack.lastOrNull()
                currentBottomBarRoute = element as BottomBarRoute
            },
            entryDecorators = listOf(
                rememberSavedStateNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator()
            ),
            entryProvider = entryProvider {
                entry<BottomBarRoute.HomeRoute>{
                    HomeNavGraph(
                        modifier = Modifier.fillMaxSize(),
                        toProfile = toProfile,
                        toSettings = {},
                    )
                }
                entry<BottomBarRoute.LibraryRoute>{
                    LibraryScreen(
                        modifier = Modifier.fillMaxSize(),
                        navigateToPlayListWhole = {
                            backStack.add(BottomBarRoute.PlaylistRoute)
                        }
                    )
                }
                entry<BottomBarRoute.PlaylistRoute> {
                    PlaylistNavGraph(
                        modifier = Modifier.fillMaxSize(),
                    )
                }
            },
            transitionSpec = { fadeIn(tween(800)) togetherWith
                    fadeOut(tween(800))
            },
            popTransitionSpec = { fadeIn(tween(800)) togetherWith
                    fadeOut(tween(800))
            },
        )
    }
}

@Composable
fun BottomMusicBar(
    modifier: Modifier = Modifier,
    currentBottomBarRoute: BottomBarRoute,
    backStack: NavBackStack,
    add: (BottomBarRoute) -> Unit
) {
    val playbackState by MusicPlayerService.playbackStateFlow.collectAsState()

    val isRunning by MusicPlayerService.isRunningFlow.collectAsState()

    val context = LocalContext.current

    val navBarItemColors = NavigationBarItemDefaults.colors(
        selectedIconColor = MaterialTheme.colorScheme.primary,
        unselectedIconColor = MaterialTheme.colorScheme.onSurface,
        selectedTextColor = MaterialTheme.colorScheme.primary,
        unselectedTextColor = MaterialTheme.colorScheme.onSurface,
        indicatorColor = Color.Transparent
    )

    Column(modifier = modifier) {
        AnimatedVisibility(
            visible = isRunning && playbackState.currentSong != null,
            enter = slideInVertically(tween(800)) { -it },
            exit = slideOutVertically(tween(800)) { -it }
        ) {
            playbackState.currentSong?.let { currentSong ->
                NowPlayingBar(
                    songTitle = currentSong.title,
                    songArtist = currentSong.artist,
                    currentDuration = playbackState.currentPosition,
                    totalDuration = playbackState.totalDuration,
                    isPlaying = playbackState.isPlaying,
                    onPlayPauseClick = { sendPlaybackAction(context, MusicPlayerService.ACTION_PLAY_PAUSE) },
                    onCloseClick = { sendPlaybackAction(context, MusicPlayerService.ACTION_STOP) },
                    onPlayingBarClick = { /* navigate to full player screen */ }
                )
            }

        }
        NavigationBar(
            modifier = modifier,
            containerColor = MaterialTheme.colorScheme.background.copy(
                alpha = 0.85f
            )
        ) {
            bottomBarItems.forEach { screen ->
                val interactionSource = remember { MutableInteractionSource() }

                NavigationBarItem(
                    modifier = Modifier.scaleOnPress({ add(screen) }),
                    selected = currentBottomBarRoute == screen,
                    icon = {
                        Icon(
                            modifier = Modifier.size(20.dp),
                            painter = painterResource(screen.icon),
                            contentDescription = null,
                        )
                    },
                    label = {
                        Text(
                            text = screen.title,
                            style = MaterialTheme.typography.labelSmall
                        )
                    },
                    interactionSource = interactionSource,
                    onClick = {
                        if (backStack.lastOrNull() != screen) {
                            add(screen)
                        }
                    },
                    colors = navBarItemColors
                )
            }
        }
    }
}

fun sendPlaybackAction(context: Context, action: String) {
    val intent = Intent(context, MusicPlayerService::class.java).apply { this.action = action }
    context.startService(intent)
}