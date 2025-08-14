package com.example.nhamngocduc.ui.playlist.playlist_detail

import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.nhamngocduc.service.MusicPlayerService
import com.example.nhamngocduc.ui.playlist.playlist_detail.components.PlayListDetailBody
import com.example.nhamngocduc.ui.playlist.playlist_detail.components.PlaylistTopBar
import org.koin.androidx.compose.koinViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PlaylistDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: PlaylistDetailViewModel = koinViewModel(),
    playlistId: Int,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    // Track the last song tap timestamp
    var lastTapTime by remember { mutableStateOf(0L) }
    val debounceDelay = 200L // milliseconds

    // Load songs for this playlist into the UI state only
    LaunchedEffect(playlistId) {
        viewModel.loadPlaylistSongs(playlistId)
    }

    Column(
        modifier = modifier.background(MaterialTheme.colorScheme.background)
    ) {
        // Top bar with sorting/view mode options
        PlaylistTopBar(
            state = state,
            onViewModeClick = {
                viewModel.processIntent(PlaylistDetailContract.Intent.ToggleViewMode)
            },
            onSortClick = {
                viewModel.processIntent(PlaylistDetailContract.Intent.ToggleSort)
            }
        )

        // Playlist body
        PlayListDetailBody(
            modifier = Modifier.fillMaxHeight(),
            state = state,
            onOptionSelected = { option, song ->
                viewModel.processIntent(
                    PlaylistDetailContract.Intent.SelectDropDownOption(option, song)
                )
            },
            onSongClick = { clickedSong ->
                val currentTime = System.currentTimeMillis()
                if (currentTime - lastTapTime < debounceDelay) return@PlayListDetailBody
                lastTapTime = currentTime

                // Update UI state in ViewModel
                viewModel.processIntent(
                    PlaylistDetailContract.Intent.PlaySong(clickedSong)
                )

                // Send explicit play request to service
                val startIndex = state.songsList.indexOf(clickedSong)
                val serviceIntent = Intent(context, MusicPlayerService::class.java).apply {
                    action = MusicPlayerService.ACTION_PLAY_SONG
                    putExtra(MusicPlayerService.EXTRA_PLAYLIST_ID, playlistId)
                    putExtra(MusicPlayerService.EXTRA_SONG_INDEX, startIndex)
                }
                context.startForegroundService(serviceIntent)
            }
        )
    }
}








