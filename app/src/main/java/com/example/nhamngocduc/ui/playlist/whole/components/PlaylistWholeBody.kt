package com.example.nhamngocduc.ui.playlist.whole.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.nhamngocduc.domain.model.Playlist
import com.example.nhamngocduc.util.DropDownOption

@Composable
fun PlaylistWholeBody(
    modifier: Modifier = Modifier,
    playlists: List<Playlist>,
    onOptionSelected: (DropDownOption, Playlist) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(start = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = playlists,
//            key = {playlist -> playlist.playlistId}
        ) { playlist ->
            PlayListItem(
                modifier = Modifier.fillMaxWidth().animateItem(),
                playlist = playlist,
                showDropDownOption = true,
                onOptionSelected = onOptionSelected
            )
        }
    }
}