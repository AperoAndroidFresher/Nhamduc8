package com.example.nhamngocduc.ui.playlist.whole

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.nhamngocduc.ui.components.PlaylistBlank
import com.example.nhamngocduc.ui.playlist.whole.components.PlaylistDialog
import com.example.nhamngocduc.ui.playlist.whole.components.PlaylistWholeBody
import com.example.nhamngocduc.ui.playlist.whole.components.PlaylistWholeTopBar
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun PlaylistWholeScreen(
    modifier: Modifier = Modifier,
    viewModel: PlaylistWholeViewModel = koinViewModel(),
    navigateToPlaylistDetail: (Int) -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val playlists by viewModel.playlists.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when(event) {
                is PlaylistWholeContract.Event.NavigateToPlaylistDetail -> {
                    navigateToPlaylistDetail(event.playlistId)
                }
            }
        }
    }

    Box(
        modifier = modifier.background(
            MaterialTheme.colorScheme.background
        ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.matchParentSize()
        ) {
            PlaylistWholeTopBar(
                modifier = Modifier.fillMaxWidth(),
                onAddNewPlaylistClick = {
                    viewModel.processIntent(PlaylistWholeContract.Intent.ShowAddDialog(true))
                }
            )
            if (playlists.isEmpty()) {
                PlaylistBlank(
                    modifier = Modifier.fillMaxSize(),
                    onAddClick = {
                        viewModel.processIntent(PlaylistWholeContract.Intent.ShowAddDialog(true))
                    }
                )
            } else {
                PlaylistWholeBody(
                    modifier = Modifier.fillMaxSize(),
                    playlists = playlists,
                    onOptionSelected = { option, playlist ->
                        viewModel.processIntent(
                            PlaylistWholeContract.Intent.SelectDropDownOption(option, playlist)
                        )
                    },
                    onPlaylistClick = { playlist ->
                        viewModel.processEvent(PlaylistWholeContract.Event.NavigateToPlaylistDetail(playlist.playlistId))
                    }
                )
            }
        }
        PlaylistDialog(
            modifier = Modifier.align(Alignment.Center).fillMaxHeight(0.25f),
            showDialog = state.showAddDialog,
            onDismissRequest = {
                viewModel.processIntent(PlaylistWholeContract.Intent.ShowAddDialog(false))
            },
            onPlaylistAction = { name ->
                viewModel.processIntent(PlaylistWholeContract.Intent.AddPlaylist(name))
                viewModel.processIntent(PlaylistWholeContract.Intent.ShowAddDialog(false))
            }
        )
        if (state.playListToRename != null) {
            PlaylistDialog(
                modifier = Modifier.align(Alignment.Center).fillMaxHeight(0.25f),
                renameDialog = true,
                showDialog = state.showRenameDialog,
                playlistName = state.playListToRename!!.playlistName,
                onDismissRequest = {
                    viewModel.processIntent(PlaylistWholeContract.Intent.HideRenameDialog)
                },
                onPlaylistAction = { newName ->
                    viewModel.processIntent(PlaylistWholeContract.Intent.RenamePlaylist(state.playListToRename!!.playlistId, newName))
                }
            )
        }
    }
}