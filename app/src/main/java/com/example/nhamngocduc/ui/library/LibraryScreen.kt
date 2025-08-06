package com.example.nhamngocduc.ui.library

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.nhamngocduc.ui.components.PlaylistDialog
import com.example.nhamngocduc.ui.library.components.LibraryTabs
import com.example.nhamngocduc.ui.library.components.LibraryTopBar
import com.example.nhamngocduc.ui.library.components.LocalList
import com.example.nhamngocduc.util.Tab
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun LibraryScreen(
    modifier: Modifier = Modifier,
    viewModel: LibraryViewModel = koinViewModel(),
    navigateToPlayListWhole: () -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    val currentTab = state.selectedTab

    val localSongs = state.localSongs

    val remoteSongs = state.remoteSongs

    val showDialog = state.showPlaylistDialog

    val selectedSong = state.selectedSong

    val playlists by viewModel.playlist.collectAsStateWithLifecycle()

    var showDialogContent by rememberSaveable { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when(event) {
                LibraryContract.Event.NavigateToPlaylistWholeScreen -> {
                    navigateToPlayListWhole()
                }
            }
        }
    }

    LaunchedEffect(showDialog) {
        if (showDialog) {
            delay(50)
            showDialogContent = true
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
            LibraryTopBar()
            LibraryTabs(
                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                currentTab = currentTab,
                onTabSelected = {
                    viewModel.processIntent(LibraryContract.Intent.SelectTab(it))
                }
            )
            when (currentTab) {
                Tab.LOCAL -> {
                    LocalList(
                        modifier = Modifier.fillMaxSize().padding(top = 8.dp),
                        songs = localSongs,
                        onOptionSelected = { option, song ->
                            viewModel.processIntent(LibraryContract.Intent.SelectDropDownOption(option, song))
                        },
                        grantPermission = {
                            viewModel.processIntent(LibraryContract.Intent.GrantPermission)
                        },
                        loadSongs = {
                            viewModel.processIntent(LibraryContract.Intent.LoadSongs)
                        }
                    )
                }
                Tab.REMOTE -> {
                    Box(
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
        PlaylistDialog(
            modifier = Modifier.align(Alignment.Center).fillMaxHeight(0.5f),
            showDialog = showDialog,
            visibleDialogContent = showDialogContent,
            playlists = playlists,
            onDismissDialog = {
                dismissDialog(
                    scope = scope,
                    showDialogContent = { showDialogContent = false }
                )
                viewModel.processIntent(LibraryContract.Intent.ShowPlaylistDialog(false))
            },
            onAddPlaylistClick = {
                showDialogContent = false
                viewModel.processIntent(LibraryContract.Intent.ShowPlaylistDialog(false))
                viewModel.processEvent(LibraryContract.Event.NavigateToPlaylistWholeScreen)
            },
            onAddSongToPlaylist = { playlist ->
                viewModel.processIntent(LibraryContract.Intent.AddSongToPlaylist(playlist))
                showDialogContent = false
                viewModel.processIntent(LibraryContract.Intent.ShowPlaylistDialog(false))
                viewModel.processIntent(LibraryContract.Intent.ResetSelectedSong)
            }
        )
    }
}

private fun dismissDialog(
    scope: CoroutineScope,
    showDialogContent: () -> Unit
) {
    scope.launch {
        showDialogContent()
        delay(500L)
    }
}