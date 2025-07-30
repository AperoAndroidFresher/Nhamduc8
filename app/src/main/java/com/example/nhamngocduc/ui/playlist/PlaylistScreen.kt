package com.example.nhamngocduc.ui.playlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.example.nhamngocduc.ui.playlist.components.PlayListBody
import com.example.nhamngocduc.ui.playlist.components.PlaylistTopBar
import com.example.nhamngocduc.util.SongList

@Composable
fun PlaylistScreen(
    modifier: Modifier = Modifier,
    viewModel: PlaylistViewModel
) {

    val state = viewModel.uiState.collectAsState()

    val songsList = SongList.songsList

    Column(
        modifier = modifier.background(
            MaterialTheme.colorScheme.background
        )
    ) {
        PlaylistTopBar(
            state = state.value,
            onViewModeClick = {
                viewModel.processIntent(PlaylistContract.Intent.ToggleViewMode)
            },
            onSortClick = {
                viewModel.processIntent(PlaylistContract.Intent.ToggleSort)
            }
        )
        PlayListBody(
            modifier = Modifier.fillMaxHeight(),
            state = state.value,
            onOptionSelected = { option, song ->
                viewModel.processIntent(PlaylistContract.Intent.SelectDropDownOption(option, song))
            }
        )
    }
}

