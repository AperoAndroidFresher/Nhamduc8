package com.example.nhamngocduc.ui.navigation.nav3

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.example.nhamngocduc.ui.profile.ProfileScreen
import com.example.nhamngocduc.ui.profile.ProfileViewModel
import com.example.nhamngocduc.ui.navigation.nav3.route.TopLevelRoute
import com.example.nhamngocduc.util.ThemeMode

@Composable
fun MainNavDisplay(
    modifier: Modifier = Modifier,
    themeMode: ThemeMode,
    onThemeChange: () -> Unit
) {
    val backStack = rememberNavBackStack<TopLevelRoute>(TopLevelRoute.AuthRoute)

    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryDecorators = listOf(
            rememberSavedStateNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider {
            entry<TopLevelRoute.AuthRoute> {
                AuthNavGraph(
                    modifier = Modifier.fillMaxSize(),
                    onLoginClick = {
                        backStack.add(TopLevelRoute.MusicRoute)
                        backStack.remove(TopLevelRoute.AuthRoute)
                    }
                )
            }
            entry<TopLevelRoute.MusicRoute> {
                MusicNavGraph(
                    modifier = Modifier.fillMaxSize(),
                    toProfile = {
                        backStack.add(TopLevelRoute.ProfileRoute)
                    },
                )
            }
            entry<TopLevelRoute.ProfileRoute> {
                ProfileScreen(
                    modifier = Modifier.fillMaxSize(),
                    themeMode = themeMode,
                    onThemeChange = onThemeChange
                )
            }
        }
    )
}