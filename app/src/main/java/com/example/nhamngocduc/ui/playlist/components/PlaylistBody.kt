package com.example.nhamngocduc.ui.playlist.components

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.nhamngocduc.data.model.Song
import com.example.nhamngocduc.util.DropDownOption
import com.example.nhamngocduc.util.ViewMode

@Composable
fun PlayListBody(
    modifier: Modifier = Modifier,
    viewMode: ViewMode,
    songItems: List<Song>,
    sortedMode: Boolean,
    dropDownItems: List<DropDownOption>,
    onOptionSelected: (DropDownOption, Song) -> Unit,
) {
    when (viewMode) {
        ViewMode.LIST ->
            SongsListView(
                modifier = modifier
                    .fillMaxSize()
                    .padding(top = 8.dp),
                songItems = songItems,
                sortedMode = sortedMode,
                dropDownItems = dropDownItems,
                onOptionSelected = onOptionSelected
            )
        ViewMode.GRID ->
            SongsGridView(
                modifier = modifier
                    .fillMaxSize()
                    .padding(top = 8.dp),
                songItems = songItems,
                dropDownItems = dropDownItems,
                onOptionSelected = onOptionSelected
            )
    }
}

@Composable
fun SongsListView(
    modifier: Modifier = Modifier,
    songItems: List<Song>,
    sortedMode: Boolean,
    dropDownItems: List<DropDownOption>,
    onOptionSelected: (DropDownOption, Song) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(start = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = songItems,
            key = {song -> song.id}
        ) { song ->
            SongListItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateItem()
                ,
                song = song,
                sortedMode = sortedMode,
                dropDownItems = dropDownItems,
                onOptionSelected = onOptionSelected
            )
        }
    }
}

@Composable
fun SongsGridView(
    modifier: Modifier = Modifier,
    songItems: List<Song>,
    dropDownItems: List<DropDownOption>,
    onOptionSelected: (DropDownOption, Song) -> Unit
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
    ) {
        items(
            items = songItems,
            key = {song -> song.id}
        ) { song ->
            SongGridItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateItem(),
                song = song,
                dropDownItems = dropDownItems,
                onOptionSelected = onOptionSelected
            )
        }
    }
}
