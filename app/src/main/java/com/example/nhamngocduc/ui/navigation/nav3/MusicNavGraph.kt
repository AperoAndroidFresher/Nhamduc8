package com.example.nhamngocduc.ui.navigation.nav3

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.example.nhamngocduc.ui.navigation.nav3.screen.BottomBarScreen
import com.example.nhamngocduc.ui.navigation.nav3.screen.BottomBarScreenSaver
import com.example.nhamngocduc.ui.navigation.nav3.screen.bottomBarItems
import com.example.nhamngocduc.ui.playlist.PlaylistScreen

@Composable
fun MusicNavGraph(
    modifier: Modifier = Modifier,
    toProfile: () -> Unit,
) {
    val backStack = rememberNavBackStack<BottomBarScreen>(BottomBarScreen.HomeScreen)

    var currentBottomBarScreen: BottomBarScreen by rememberSaveable(
        stateSaver = BottomBarScreenSaver
    ) { mutableStateOf(BottomBarScreen.HomeScreen) }

    Scaffold(
        modifier = modifier,
        topBar = {

        },
        bottomBar = {
            BottomMusicBar(
                modifier = Modifier.fillMaxWidth(),
                currentBottomBarScreen = currentBottomBarScreen,
                backStack = backStack,
                add = { newScreen ->
                    backStack.add(newScreen)
                },
                updateCurrentScreen = { newScreen ->
                    currentBottomBarScreen = newScreen
                }
            )
        }
    ) { paddingValues ->
        NavDisplay(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            backStack = backStack,
            onBack = {
                backStack.removeLastOrNull()
                val element = backStack.lastOrNull()
                currentBottomBarScreen = element as BottomBarScreen
            },
            entryDecorators = listOf(
                rememberSavedStateNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator()
            ),
            entryProvider = entryProvider {
                entry<BottomBarScreen.HomeScreen>{
                    Box(
                        modifier = Modifier
                            .background(Color.White)
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        TextButton(
                            onClick = toProfile
                        ) {
                            Text("To Profile")
                        }
                    }
                }
                entry<BottomBarScreen.LibraryScreen>{
                    Box(
                        modifier = Modifier
                            .background(Color.White)
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Library")
                    }
                }
                entry<BottomBarScreen.PlaylistScreen> {
                    PlaylistScreen(
                        modifier = Modifier.fillMaxSize()
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
    currentBottomBarScreen: BottomBarScreen,
    backStack: NavBackStack,
    add: (BottomBarScreen) -> Unit,
    updateCurrentScreen: (BottomBarScreen) -> Unit,
) {
    val navBarItemColors = NavigationBarItemDefaults.colors(
        selectedIconColor = MaterialTheme.colorScheme.primary,
        unselectedIconColor = MaterialTheme.colorScheme.onSurface,
        selectedTextColor = MaterialTheme.colorScheme.primary,
        unselectedTextColor = MaterialTheme.colorScheme.onSurface,
        indicatorColor = MaterialTheme.colorScheme.background
    )

    NavigationBar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.background
    ) {
        bottomBarItems.forEach { screen ->
            NavigationBarItem(
                selected = currentBottomBarScreen == screen,
                icon = {
                    Icon(
                        modifier = Modifier.size(16.dp),
                        painter = painterResource(screen.icon),
                        contentDescription = null,
                    )
                },
                label = {
                    Text(
                        text = screen.title,
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                },
                onClick = {
                    if (backStack.lastOrNull() != screen) {

                        add(screen)
                        updateCurrentScreen(screen)
                    }
                },
                colors = navBarItemColors
            )
        }
    }
}