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

    private var currentPlaylistId: Int = -1

    fun processIntent(intent: PlaylistDetailContract.Intent) {
        when (intent) {
            is PlaylistDetailContract.Intent.ToggleSort -> _uiState.update { it.copy(isSorted = !it.isSorted) }

            is PlaylistDetailContract.Intent.ToggleViewMode -> _uiState.update {
                it.copy(
                    viewMode = if (it.viewMode == ViewMode.LIST) ViewMode.GRID else ViewMode.LIST
                )
            }

            is PlaylistDetailContract.Intent.SelectDropDownOption -> {
                when (intent.option) {
                    DropDownOption.REMOVE -> {
                        scope.launch {
                            playlistUseCases.removeSongFromPlaylist(
                                currentPlaylistId,
                                intent.song.songId
                            )
                        }
                    }

                    DropDownOption.SHARE -> {}
                    else -> {}
                }
            }

            is PlaylistDetailContract.Intent.LoadPlaylistDetail -> {
                loadPlaylistSongs(intent.playlistId)
            }

            is PlaylistDetailContract.Intent.PlaySong -> {
                val index = _uiState.value.songsList.indexOf(intent.clickedSong)
                _uiState.update { it.copy(startIndex = index, currentPlayingSong = intent.clickedSong) }
            }
        }
    }

        fun loadPlaylistSongs(playlistId: Int) {
            currentPlaylistId = playlistId
            scope.launch {
                playlistUseCases.getSongsFromPlaylist(playlistId).collect { playlistWithSongs ->
                    playlistUseCases.getSongsFromPlaylist(playlistId).collect { playlistWithSongs ->
                        _uiState.update { it.copy(
                            playlist = playlistWithSongs?.playlist,
                            songsList = playlistWithSongs?.songs ?: emptyList()
                        )}
                    }
                }
            }
        }

}
