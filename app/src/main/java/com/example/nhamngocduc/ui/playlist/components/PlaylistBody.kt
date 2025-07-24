package com.example.nhamngocduc.ui.playlist.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
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
import com.example.nhamngocduc.ui.playlist.ViewMode

@Composable
fun PlayListBody(
    modifier: Modifier = Modifier,
    viewMode: ViewMode,
    songItems: List<Song>,
    onRemoveClick: (Int) -> Unit,
) {
    val dropDownItems = listOf<String>("Remove")

    when (viewMode) {
        ViewMode.LIST ->
            SongsListView(
                modifier = modifier
                    .fillMaxSize()
                    .padding(top = 16.dp),
                songItems = songItems,
                dropDownItems = dropDownItems,
                onRemoveClick = onRemoveClick
            )
        ViewMode.GRID ->
            SongsGridView(
                modifier = modifier
                    .fillMaxSize()
                    .padding(top = 16.dp),
                songItems = songItems,
                dropDownItems = dropDownItems,
                onRemoveClick = onRemoveClick
            )
    }
}

@Composable
fun SongsListView(
    modifier: Modifier = Modifier,
    songItems: List<Song>,
    dropDownItems: List<String>,
    onRemoveClick: (Int) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp),
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
                dropDownItems = dropDownItems,
                onDeleteItem = onRemoveClick
            )
        }
    }
}

@Composable
fun SongsGridView(
    modifier: Modifier = Modifier,
    songItems: List<Song>,
    dropDownItems: List<String>,
    onRemoveClick: (Int) -> Unit
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
                onDeleteItem = onRemoveClick,
            )
        }
    }
}
