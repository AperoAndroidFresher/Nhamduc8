package com.example.nhamngocduc.ui.playlist

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.nhamngocduc.ui.playlist.components.PlayListBody
import com.example.nhamngocduc.ui.playlist.components.PlaylistTopBar
import com.example.nhamngocduc.util.SongList

@Composable
fun PlaylistScreen(
    modifier: Modifier = Modifier,
) {
    var viewMode by rememberSaveable {
        mutableStateOf(ViewMode.LIST)
    }

    Scaffold(
        topBar = {
            PlaylistTopBar(
                viewMode = viewMode,
                onViewModeClick = {
                    when(viewMode) {
                        ViewMode.GRID -> viewMode = ViewMode.LIST
                        ViewMode.LIST -> viewMode = ViewMode.GRID
                    }
                },
                onSortClick = {}
            )
        },
        content = { paddingValues ->
            PlayListBody(
                modifier = Modifier.padding(paddingValues),
                viewMode = viewMode,
                songItems = SongList.songsList,
                onRemoveClick = {
                    SongList.removeItem(it)
                }
            )
        },
        containerColor = Color.Black
    )
}

