package com.example.nhamngocduc.ui.navigation.nav3.route

import androidx.navigation3.runtime.NavKey
import com.example.nhamngocduc.domain.model.AlbumDomain
import com.example.nhamngocduc.domain.model.ArtistDomain
import com.example.nhamngocduc.domain.model.TrackDomain
import kotlinx.serialization.Serializable

@Serializable
sealed interface HomeRoute : NavKey {
    @Serializable
    data object HomeMainRoute : HomeRoute
    @Serializable
    data class TopAlbumsRoute(val albums: List<AlbumDomain>) : HomeRoute
    @Serializable
    data class TopArtistsRoute(val artists: List<ArtistDomain>) : HomeRoute
    @Serializable
    data class TopSongsRoute(val tracks: List<TrackDomain>) : HomeRoute

}