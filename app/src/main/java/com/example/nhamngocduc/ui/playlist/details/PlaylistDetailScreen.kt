package com.example.nhamngocduc.ui.playlist.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nhamngocduc.ui.playlist.details.components.PlayListDetailBody
import com.example.nhamngocduc.ui.playlist.components.PlaylistTopBar
import com.google.accompanist.permissions.ExperimentalPermissionsApi

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PlaylistDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: PlaylistDetailViewModel = viewModel(),
) {

    val state = viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = modifier.background(
            MaterialTheme.colorScheme.background
        )
    ) {
        PlaylistTopBar(
            state = state.value,
            onViewModeClick = {
                viewModel.processIntent(PlaylistDetailContract.Intent.ToggleViewMode)
            },
            onSortClick = {
                viewModel.processIntent(PlaylistDetailContract.Intent.ToggleSort)
            }
        )
        PlayListDetailBody(
            modifier = Modifier.fillMaxHeight(),
            state = state.value,
            onOptionSelected = { option, song ->
                viewModel.processIntent(PlaylistDetailContract.Intent.SelectDropDownOption(option, song))
            }
        )
    }
}

