package com.example.nhamngocduc.ui.library.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.nhamngocduc.domain.model.Song
import com.example.nhamngocduc.ui.components.SongListItem
import com.example.nhamngocduc.util.DropDownOption
import com.example.nhamngocduc.util.libraryDropDownOptions

@Composable
fun RemoteList(
    modifier: Modifier = Modifier,
    songs: List<Song>,
    onOptionSelected: (DropDownOption, Song) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(start = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = songs,
//            key = {song -> song.songId}
        ) { song ->
            SongListItem(
                modifier = Modifier,
                song = song,
                dropDownItems = libraryDropDownOptions,
                onOptionSelected = onOptionSelected,
            )
        }
    }
}