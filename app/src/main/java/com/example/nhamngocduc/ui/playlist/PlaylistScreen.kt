package com.example.nhamngocduc.ui.playlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.nhamngocduc.ui.playlist.components.PlayListBody
import com.example.nhamngocduc.ui.playlist.components.PlaylistTopBar
import com.example.nhamngocduc.util.RemoveOption
import com.example.nhamngocduc.util.ShareOption
import com.example.nhamngocduc.util.SongList
import com.example.nhamngocduc.util.ViewMode

@Composable
fun PlaylistScreen(
    modifier: Modifier = Modifier,
) {
    var viewMode by rememberSaveable {
        mutableStateOf(ViewMode.LIST)
    }

    var sortedMode by rememberSaveable {
        mutableStateOf(false)
    }

//    val dropDownItems = listOf(
//        DropDownOption.REMOVE,
//        DropDownOption.SHARE
//    )

    val dropDownItems = listOf(
        RemoveOption(),
        ShareOption()
    )

    val songsList = SongList.songsList

    Column(
        modifier = modifier.background(
            MaterialTheme.colorScheme.background
        )
    ) {
        PlaylistTopBar(
            viewMode = viewMode,
            sortedMode = sortedMode,
            onViewModeClick = {
                viewMode = when(viewMode) {
                    ViewMode.GRID -> ViewMode.LIST
                    ViewMode.LIST -> ViewMode.GRID
                }
            },
            onSortClick = {
                sortedMode = !sortedMode
            }
        )
        PlayListBody(
            modifier = Modifier.fillMaxHeight(),
            viewMode = viewMode,
            songItems = songsList,
            sortedMode = sortedMode,
            dropDownItems = dropDownItems,
            onOptionSelected = { option, song ->
                option.execute(song)
            }
        )
    }
}

