package com.example.nhamngocduc.ui.navigation.nav3.screen

import androidx.annotation.DrawableRes
import androidx.compose.runtime.saveable.Saver
import androidx.navigation3.runtime.NavKey
import com.example.nhamngocduc.R
import com.example.nhamngocduc.ui.navigation.nav3.screen.BottomBarScreen.HomeScreen
import com.example.nhamngocduc.ui.navigation.nav3.screen.BottomBarScreen.LibraryScreen
import com.example.nhamngocduc.ui.navigation.nav3.screen.BottomBarScreen.PlaylistScreen
import kotlinx.serialization.Serializable

val bottomBarItems = listOf<BottomBarScreen>(
    BottomBarScreen.HomeScreen,
    BottomBarScreen.LibraryScreen,
    BottomBarScreen.PlaylistScreen
)
@Serializable
sealed class BottomBarScreen(
    @DrawableRes val icon: Int,
    val title: String
) : NavKey {
    @Serializable
    data object HomeScreen : BottomBarScreen(
        icon = R.drawable.ic_home,
        title = "Home"
    )

    @Serializable
    data object LibraryScreen : BottomBarScreen(
        icon = R.drawable.ic_library,
        title = "Library"
    )

    @Serializable
    data object  PlaylistScreen: BottomBarScreen(
        icon = R.drawable.ic_playlist,
        title = "Playlist"
    )
}

val BottomBarScreenSaver = Saver<BottomBarScreen, String>(
    save = { BottomBarScreen::class.simpleName },
    restore = {
        when(it) {
            HomeScreen::class.simpleName ->
                HomeScreen
            LibraryScreen::class.simpleName ->
                LibraryScreen
            PlaylistScreen::class.simpleName ->
                PlaylistScreen
            else -> HomeScreen
        }
    }
)