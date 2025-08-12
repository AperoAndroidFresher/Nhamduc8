package com.example.nhamngocduc.ui.navigation.nav3.route

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface PlaylistRoute : NavKey {
    @Serializable
    data object PlaylistWholeRoute : PlaylistRoute
    @Serializable
    data class PlaylistDetailRoute(val playlistId: Int) : PlaylistRoute
}