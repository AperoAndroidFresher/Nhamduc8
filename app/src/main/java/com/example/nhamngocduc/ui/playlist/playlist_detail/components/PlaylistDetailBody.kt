package com.example.nhamngocduc.ui.playlist.playlist_detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.nhamngocduc.domain.model.Song
import com.example.nhamngocduc.service.MusicPlayerService
import com.example.nhamngocduc.ui.components.lazy_item.SongGridItem
import com.example.nhamngocduc.ui.components.lazy_item.SongListItem
import com.example.nhamngocduc.ui.playlist.playlist_detail.PlaylistDetailContract
import com.example.nhamngocduc.util.DropDownOption
import com.example.nhamngocduc.util.ViewMode
import com.example.nhamngocduc.util.playlistDropDownOptions

@Composable
fun PlayListDetailBody(
    modifier: Modifier = Modifier,
    state: PlaylistDetailContract.State,
    onOptionSelected: (DropDownOption, Song) -> Unit,
    onSongClick: (Song) -> Unit
) {
    val dropDownItems = playlistDropDownOptions
    val viewMode = state.viewMode
    val isSortedMode = state.isSorted
    val songItems = state.songsList

    when (viewMode) {
        ViewMode.LIST ->
            SongsListView(
                modifier = modifier
                    .fillMaxSize()
                    .padding(top = 8.dp),
                songItems = songItems,
                sortedMode = isSortedMode,
                dropDownItems = dropDownItems,
                onOptionSelected = onOptionSelected,
                onSongClick = onSongClick
            )
        ViewMode.GRID ->
            SongsGridView(
                modifier = modifier
                    .fillMaxSize()
                    .padding(top = 8.dp),
                songItems = songItems,
                dropDownItems = dropDownItems,
                onOptionSelected = onOptionSelected,
                onSongClick
            )
    }
}

@Composable
fun SongsListView(
    modifier: Modifier = Modifier,
    songItems: List<Song>,
    sortedMode: Boolean,
    dropDownItems: List<DropDownOption>,
    onOptionSelected: (DropDownOption, Song) -> Unit,
    onSongClick: (Song) -> Unit
) {
    val playbackState by MusicPlayerService.playbackStateFlow.collectAsState()

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = songItems,
//            key = {song -> song.songId}
        ) { song ->
            val isSelected = playbackState.currentSong?.songId == song.songId

            SongListItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateItem(),
                song = song,
                isSelected = isSelected,
                sortedMode = sortedMode,
                dropDownItems = dropDownItems,
                onOptionSelected = onOptionSelected,
                onSongClick = onSongClick
            )
        }
    }
}

@Composable
fun SongsGridView(
    modifier: Modifier = Modifier,
    songItems: List<Song>,
    dropDownItems: List<DropDownOption>,
    onOptionSelected: (DropDownOption, Song) -> Unit,
    onSongClick: (Song) -> Unit
) {
    val playbackState by MusicPlayerService.playbackStateFlow.collectAsState()

    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
    ) {
        items(
            items = songItems,
            key = {song -> song.songId}
        ) { song ->
            val isSelected = playbackState.currentSong?.songId == song.songId

            SongGridItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateItem(),
                song = song,
                isSelected = isSelected,
                dropDownItems = dropDownItems,
                onOptionSelected = onOptionSelected,
                onSongClick = onSongClick
            )
        }
    }
}
