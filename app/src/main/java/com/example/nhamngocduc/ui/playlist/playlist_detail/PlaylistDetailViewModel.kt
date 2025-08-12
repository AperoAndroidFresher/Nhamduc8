package com.example.nhamngocduc.ui.playlist.playlist_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nhamngocduc.domain.usecases.playlist.PlaylistUseCases
import com.example.nhamngocduc.util.DropDownOption
import com.example.nhamngocduc.util.ViewMode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PlaylistDetailViewModel(
    private val playlistUseCases: PlaylistUseCases
) : ViewModel() {
    private val _uiState = MutableStateFlow(PlaylistDetailContract.State())
    val uiState: StateFlow<PlaylistDetailContract.State> = _uiState.asStateFlow()

    private val scope = viewModelScope

    fun processIntent(intent: PlaylistDetailContract.Intent) {
        when(intent) {
            is PlaylistDetailContract.Intent.ToggleSort -> _uiState.update { it.copy(isSorted = !it.isSorted) }

            is PlaylistDetailContract.Intent.ToggleViewMode -> _uiState.update { it.copy(
                viewMode = if (it.viewMode == ViewMode.LIST) ViewMode.GRID else ViewMode.LIST
            ) }

            is PlaylistDetailContract.Intent.SelectDropDownOption -> {
                when (intent.option) {
                    DropDownOption.REMOVE -> {
                        val updatedSongs = _uiState.value.songsList.filter { it.songId != intent.song.songId }
                        _uiState.update { it.copy(songsList = updatedSongs) }
                    }
                    DropDownOption.SHARE -> {}
                    else -> {}
                }
            }
        }
    }

    fun loadPlaylistSongs(playlistId: Int) {
        scope.launch {
            playlistUseCases.getSongsFromPlaylist(playlistId).collect { playlistWithSongs ->
                if (playlistWithSongs != null) {
                    _uiState.update {
                        it.copy(
                            playlist = playlistWithSongs.playlist,
                            songsList = playlistWithSongs.songs,
                        )
                    }
                } else {
                    _uiState.update { it.copy(songsList = emptyList()) }
                }
            }
        }
    }

}