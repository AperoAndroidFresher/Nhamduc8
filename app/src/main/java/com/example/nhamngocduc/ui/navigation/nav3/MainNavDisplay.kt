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
import com.example.nhamngocduc.ui.editor.EditScreen
import com.example.nhamngocduc.ui.navigation.nav3.screen.TopLevelScreen
import com.example.nhamngocduc.util.ThemeMode

@Composable
fun MainNavDisplay(
    modifier: Modifier = Modifier,
    themeMode: ThemeMode,
    onThemeChange: () -> Unit
) {
    val backStack = rememberNavBackStack<TopLevelScreen>(TopLevelScreen.AuthScreen)

    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryDecorators = listOf(
            rememberSavedStateNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider {
            entry<TopLevelScreen.AuthScreen> {
                AuthNavGraph(
                    modifier = Modifier.fillMaxSize(),
                    onLoginClick = {
                        backStack.add(TopLevelScreen.MusicScreen)
                        backStack.remove(TopLevelScreen.AuthScreen)
                    }
                )
            }
            entry<TopLevelScreen.MusicScreen> {
                MusicNavGraph(
                    modifier = Modifier.fillMaxSize(),
                    toProfile = {
                        backStack.add(TopLevelScreen.ProfileScreen)
                    },
                )
            }
            entry<TopLevelScreen.ProfileScreen> {
                EditScreen(
                    modifier = Modifier.fillMaxSize(),
                    themeMode = themeMode,
                    onThemeChange = onThemeChange
                )
            }
        }
    )
}