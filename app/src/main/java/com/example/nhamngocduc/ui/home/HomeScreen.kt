package com.example.nhamngocduc.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.nhamngocduc.R
import com.example.nhamngocduc.domain.model.AlbumDomain
import com.example.nhamngocduc.domain.model.ArtistDomain
import com.example.nhamngocduc.domain.model.TrackDomain
import com.example.nhamngocduc.ui.components.animation.LottieAnimation
import com.example.nhamngocduc.ui.home.components.WelcomeHeader
import com.example.nhamngocduc.ui.library.components.ErrorScreen
import org.koin.androidx.compose.koinViewModel
import androidx.compose.runtime.collectAsState
import com.example.nhamngocduc.ui.home.components.TopAlbumsSection
import com.example.nhamngocduc.ui.home.components.TopArtistsSection
import com.example.nhamngocduc.ui.home.components.TopTracksSection

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinViewModel(),
    navigateToAlbums: (List<AlbumDomain>) -> Unit,
    navigateToTracks: (List<TrackDomain>) -> Unit,
    navigateToArtists: (List<ArtistDomain>) -> Unit,
    navigateToProfile: () -> Unit,
    navigateToSettings: () -> Unit,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.event.collect {
            when(it) {
                is HomeContract.Event.NavigateToTopAlbums -> navigateToAlbums(it.albums)
                is HomeContract.Event.NavigateToTopTracks -> navigateToTracks(it.tracks)
                is HomeContract.Event.NavigateToTopArtists -> navigateToArtists(it.artists)
                is HomeContract.Event.NavigateToProfile -> navigateToProfile()
                is HomeContract.Event.NavigateToSettings -> navigateToSettings()
            }
        }
    }

    val username = viewModel.username.collectAsState().value

    val profileImageUri = viewModel.profilePicture

    Column(
        modifier = modifier.padding(horizontal = 16.dp)
    ) {
        WelcomeHeader(
            modifier = Modifier.fillMaxWidth(),
            profileImageUri = profileImageUri,
            userName = username,
            onProfileImageClick = {
                viewModel.processEvent(HomeContract.Event.NavigateToProfile)
            },
            onSettingsClick = {
                viewModel.processEvent(HomeContract.Event.NavigateToSettings)
            }
        )

        when (state) {
            is HomeContract.State.Loading -> {
                LottieAnimation(modifier = modifier, resId = R.raw.lottie_remote_item_loading)
            }
            is HomeContract.State.Success -> {
                val uistate = state as HomeContract.State.Success
                HomeContent(
                    modifier = Modifier.weight(1f),
                    topAlbums = uistate.topAlbums,
                    topTracks = uistate.topTracks,
                    topArtists = uistate.topArtists,
                    onSeeAllAlbums = {
                        viewModel.processEvent(HomeContract.Event.NavigateToTopAlbums(uistate.topAlbums))
                    },
                    onSeeAllTracks = {
                        viewModel.processEvent(HomeContract.Event.NavigateToTopTracks(uistate.topTracks))
                    },
                    onSeeAllArtists = {
                        viewModel.processEvent(HomeContract.Event.NavigateToTopArtists(uistate.topArtists))
                    },
                )
            }
            is HomeContract.State.Error -> {
                val uistate = state as HomeContract.State.Error
                ErrorScreen(modifier = modifier, text = uistate.message) { viewModel.processIntent(HomeContract.Intent.LoadHomeData) }
            }
        }
    }
}

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    topAlbums: List<AlbumDomain>,
    topTracks: List<TrackDomain>,
    topArtists: List<ArtistDomain>,
    onSeeAllAlbums: () -> Unit,
    onSeeAllTracks: () -> Unit,
    onSeeAllArtists: () -> Unit
) {

    LazyColumn(
        modifier = modifier
            .background(Color.Black)
    ) {
        // Albums
        item {
            TopAlbumsSection(
                modifier = Modifier.fillMaxWidth(),
                list = topAlbums.take(8),
                onSeeAllClick = onSeeAllAlbums
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        // Tracks
        item {
            TopTracksSection(
                modifier = Modifier.fillMaxWidth(),
                list = topTracks.take(17),
                onSeeAllClick = onSeeAllTracks
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Artists
        item {
            TopArtistsSection(
                modifier = Modifier.fillMaxWidth(),
                artists = topArtists.take(24),
                onSeeAllClick = onSeeAllArtists
            )
        }
        item {
            Spacer(modifier = Modifier.height(128.dp))
        }
    }
}

