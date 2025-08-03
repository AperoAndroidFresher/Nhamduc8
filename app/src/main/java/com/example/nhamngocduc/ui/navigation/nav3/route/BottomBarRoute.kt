package com.example.nhamngocduc.ui.navigation.nav3.route

import androidx.navigation3.runtime.NavKey
import com.example.nhamngocduc.R
import com.example.nhamngocduc.ui.navigation.nav3.route.BottomBarRoute.HomeRoute
import com.example.nhamngocduc.ui.navigation.nav3.route.BottomBarRoute.LibraryRoute
import com.example.nhamngocduc.ui.navigation.nav3.route.BottomBarRoute.PlaylistRoute
import kotlinx.serialization.Serializable

val bottomBarItems = listOf(
    HomeRoute,
    LibraryRoute,
    PlaylistRoute
)

@Serializable
sealed class BottomBarRoute(
    val icon: Int,
    val title: String
) : NavKey {
    @Serializable
    data object HomeRoute : BottomBarRoute(
        icon = R.drawable.ic_home,
        title = "Home"
    )

    @Serializable
    data object LibraryRoute : BottomBarRoute(
        icon = R.drawable.ic_library,
        title = "Library"
    )

    @Serializable
    data object  PlaylistRoute: BottomBarRoute(
        icon = R.drawable.ic_playlist,
        title = "Playlist"
    )
}