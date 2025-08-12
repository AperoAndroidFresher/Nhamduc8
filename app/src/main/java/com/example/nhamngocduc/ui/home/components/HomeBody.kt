package com.example.nhamngocduc.ui.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import com.example.nhamngocduc.domain.model.AlbumDomain
import com.example.nhamngocduc.domain.model.ArtistDomain
import com.example.nhamngocduc.domain.model.TrackDomain

@Composable
fun HomeBody(
    modifier: Modifier = Modifier,
    topAlbums: List<AlbumDomain>,
    topTracks: List<TrackDomain>,
    topArtists: List<ArtistDomain>,
    onSeeAllAlbum: () -> Unit,
    onSeeAllTracks: () -> Unit,
    onSeeAllArtists: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .background(Color.Black),
        contentPadding = PaddingValues(16.dp)
    ) {

    }
}