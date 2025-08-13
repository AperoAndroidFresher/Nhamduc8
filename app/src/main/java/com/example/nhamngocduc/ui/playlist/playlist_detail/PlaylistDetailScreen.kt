package com.example.nhamngocduc.ui.playlist.playlist_detail

import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.nhamngocduc.domain.model.Song
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
                // Update ViewModel state for UI
                viewModel.processIntent(
                    PlaylistDetailContract.Intent.PlaySong(clickedSong)
                )

                // Only send playlistId and startIndex to service
                val startIndex = state.songsList.indexOf(clickedSong)
                val serviceIntent = Intent(context, MusicPlayerService::class.java).apply {
                    action = MusicPlayerService.ACTION_PLAY_PLAYLIST
                    putExtra(MusicPlayerService.EXTRA_PLAYLIST_ID, state.playlist?.playlistId ?: -1)
                    putExtra(MusicPlayerService.EXTRA_START_INDEX, startIndex)
                }
                context.startForegroundService(serviceIntent)
            }
        )
    }
}




