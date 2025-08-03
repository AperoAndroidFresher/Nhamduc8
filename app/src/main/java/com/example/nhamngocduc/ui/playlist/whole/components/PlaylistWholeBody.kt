package com.example.nhamngocduc.ui.playlist.whole.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.nhamngocduc.domain.model.Playlist
import com.example.nhamngocduc.util.DropDownOption

@Composable
fun PlaylistWholeBody(
    modifier: Modifier = Modifier,
    playlists: List<Playlist>,
    onOptionSelected: (DropDownOption, Playlist) -> Unit
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(
            items = playlists,
            key = {playlist -> playlist.id}
        ) { playlist ->
            PlayListItem(
                modifier = Modifier.fillMaxWidth(),
                playlist = playlist,
                showDropDownOption = true,
                onOptionSelected = onOptionSelected
            )
        }
    }
}