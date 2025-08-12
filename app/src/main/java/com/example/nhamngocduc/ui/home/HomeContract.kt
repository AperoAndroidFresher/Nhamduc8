package com.example.nhamngocduc.ui.home

import androidx.navigation3.runtime.NavKey
import com.example.nhamngocduc.domain.model.AlbumDomain
import com.example.nhamngocduc.domain.model.ArtistDomain
import com.example.nhamngocduc.domain.model.TrackDomain

object HomeContract {
    sealed interface State {
        object Loading : State
        data class Success(
            val topAlbums: List<AlbumDomain>,
            val topArtists: List<ArtistDomain>,
            val topTracks: List<TrackDomain>
        ) : State
        data class Error(val message: String) : State
    }

    sealed interface Intent {
        object LoadHomeData : Intent
    }

    sealed interface Event {
        data class NavigateToTopAlbums(val albums: List<AlbumDomain>) : Event
        data class NavigateToTopTracks(val tracks: List<TrackDomain>) : Event
        data class NavigateToTopArtists(val artists: List<ArtistDomain>) : Event
        data object NavigateToProfile : Event
        data object NavigateToSettings : Event
    }
}