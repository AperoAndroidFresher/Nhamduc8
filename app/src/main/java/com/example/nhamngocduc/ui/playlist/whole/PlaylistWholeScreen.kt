package com.example.nhamngocduc.ui.playlist.whole

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.nhamngocduc.R
import com.example.nhamngocduc.domain.model.Playlist
import com.example.nhamngocduc.ui.components.PlaylistBlank
import com.example.nhamngocduc.ui.components.ScaledIconButton
import com.example.nhamngocduc.ui.playlist.whole.components.PlaylistWholeBody
import com.example.nhamngocduc.ui.playlist.whole.components.PlaylistWholeTopBar
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun PlaylistWholeScreen(
    modifier: Modifier = Modifier,
    viewModel: PlaylistWholeViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val playlists by viewModel.playlists.collectAsStateWithLifecycle()

    Column {
        PlaylistWholeTopBar(
            modifier = Modifier.fillMaxWidth(),
            onAddNewPlaylistClick = {}
        )
        if (playlists.isEmpty()) {
            PlaylistBlank(
                modifier = Modifier.fillMaxSize(),
                onAddClick = {

                }
            )
        } else {
            PlaylistWholeBody(
                modifier = Modifier.fillMaxSize(),
                playlists = playlists,
                onOptionSelected = { option, playlist ->
                    viewModel.processIntent(PlaylistWholeContract.Intent.SelectDropDownOption(option, playlist))
                }
            )
        }
    }
}