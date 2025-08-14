package com.example.nhamngocduc.ui.navigation.nav3

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.example.nhamngocduc.service.MusicPlayerService
import com.example.nhamngocduc.ui.components.animation.scaleOnPress
import com.example.nhamngocduc.ui.library.LibraryScreen
import com.example.nhamngocduc.ui.navigation.nav3.route.BottomBarRoute
import com.example.nhamngocduc.ui.navigation.nav3.route.BottomBarRoute.HomeRoute
import com.example.nhamngocduc.ui.navigation.nav3.route.BottomBarRoute.LibraryRoute
import com.example.nhamngocduc.ui.navigation.nav3.route.BottomBarRoute.PlaylistRoute
import com.example.nhamngocduc.ui.now_playing.NowPlayingBar
import com.example.nhamngocduc.ui.now_playing.NowPlayingScreen
import com.example.nhamngocduc.ui.now_playing.PlaybackState

val bottomBarItems = listOf(
    HomeRoute,
    LibraryRoute,
    PlaylistRoute
)

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MusicNavGraph(
    modifier: Modifier = Modifier,
    toProfile: () -> Unit,
) {
    val playbackState by MusicPlayerService.playbackStateFlow.collectAsStateWithLifecycle()
    val context = LocalContext.current

    val backStack = rememberNavBackStack<BottomBarRoute>(BottomBarRoute.HomeRoute)
    var currentBottomBarRoute: BottomBarRoute = backStack.lastOrNull() as? BottomBarRoute ?: BottomBarRoute.HomeRoute

    Scaffold(
        modifier = modifier,
        bottomBar = {
            if (currentBottomBarRoute in bottomBarItems) {
                BottomMusicBar(
                    modifier = Modifier.fillMaxWidth(),
                    playbackState = playbackState,
                    currentBottomBarRoute = currentBottomBarRoute,
                    backStack = backStack,
                    add = { newScreen ->
                        backStack.add(newScreen)
                    }
                )
            }
        }
    ) { paddingValues ->
        val paddingTop = paddingValues.calculateTopPadding()
        val paddingStart = paddingValues.calculateStartPadding(LayoutDirection.Ltr)
        val paddingEnd = paddingValues.calculateEndPadding(LayoutDirection.Ltr)

        NavDisplay(
            modifier = Modifier.padding(top = paddingTop, start = paddingStart, end = paddingEnd),
            backStack = backStack,
            onBack = {
                backStack.removeLastOrNull()
                val element = backStack.lastOrNull()
                currentBottomBarRoute = element as? BottomBarRoute ?: BottomBarRoute.HomeRoute
            },
            entryDecorators = listOf(
                rememberSavedStateNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator()
            ),
            entryProvider = entryProvider {
                entry<BottomBarRoute.HomeRoute> {
                    HomeNavGraph(
                        modifier = Modifier.fillMaxSize(),
                        toProfile = toProfile,
                        toSettings = {}
                    )
                }
                entry<BottomBarRoute.LibraryRoute> {
                    LibraryScreen(
                        modifier = Modifier.fillMaxSize(),
                        navigateToPlayListWhole = { backStack.add(BottomBarRoute.PlaylistRoute) }
                    )
                }
                entry<BottomBarRoute.PlaylistRoute> {
                    PlaylistNavGraph(modifier = Modifier.fillMaxSize())
                }
                entry<BottomBarRoute.NowPlayingRoute> {
                    NowPlayingScreen(
                        playbackState = playbackState,
                        onPlayOrPauseClick = { sendPlaybackAction(context, MusicPlayerService.ACTION_PLAY_PAUSE) },
                        onSkipPreviousClick = { sendPlaybackAction(context, MusicPlayerService.ACTION_PREV) },
                        onSkipNextClick = { sendPlaybackAction(context, MusicPlayerService.ACTION_NEXT) },
                        onSeek = { pos ->
                            sendPlaybackSeekAction(context, pos)
                        },
                        onShuffleClick = { sendPlaybackAction(context, MusicPlayerService.ACTION_TOGGLE_SHUFFLE) },
                        onRepeatClick = { sendPlaybackAction(context, MusicPlayerService.ACTION_TOGGLE_REPEAT) }
                    )
                }
            },
            transitionSpec = { fadeIn(tween(300)) togetherWith fadeOut(tween(300)) },
            popTransitionSpec = { fadeIn(tween(300)) togetherWith fadeOut(tween(300)) }
        )
    }
}

@Composable
fun BottomMusicBar(
    modifier: Modifier = Modifier,
    playbackState: PlaybackState,
    currentBottomBarRoute: BottomBarRoute,
    backStack: NavBackStack,
    add: (BottomBarRoute) -> Unit
) {
    val isRunning by MusicPlayerService.isRunningFlow.collectAsStateWithLifecycle()

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
                    playbackState = playbackState,
                    onPlayPauseClick = { sendPlaybackAction(context, MusicPlayerService.ACTION_PLAY_PAUSE) },
                    onCloseClick = { sendPlaybackAction(context, MusicPlayerService.ACTION_STOP) },
                    onPlayingBarClick = { backStack.add(BottomBarRoute.NowPlayingRoute) }
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

fun sendPlaybackSeekAction(context: Context, positionMs: Long) {
    val intent = Intent(context, MusicPlayerService::class.java).apply {
        action = MusicPlayerService.ACTION_SEEK
        putExtra(MusicPlayerService.EXTRA_SEEK_POSITION, positionMs)
    }
    context.startService(intent)
}