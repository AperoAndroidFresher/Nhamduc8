package com.example.nhamngocduc.ui.playlist.whole

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nhamngocduc.domain.model.Playlist
import com.example.nhamngocduc.domain.usecases.playlist.PlaylistUseCases
import com.example.nhamngocduc.util.DropDownOption
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PlaylistWholeViewModel(
    private val playlistUseCases: PlaylistUseCases
) : ViewModel() {
    private val _uiState = MutableStateFlow(PlaylistWholeContract.State())
    val uiState = _uiState.asStateFlow()

    val playlists: StateFlow<List<Playlist>> = playlistUseCases.getAllPlaylists()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )

    fun processIntent(intent: PlaylistWholeContract.Intent) {
        when(intent) {
            is PlaylistWholeContract.Intent.RemovePlaylist -> {
                viewModelScope.launch {
                    playlistUseCases.removePlaylist(intent.playlist.playlistId)
                }
            }
            is PlaylistWholeContract.Intent.AddPlaylist -> {
                viewModelScope.launch {
                    playlistUseCases.addNewPlaylist(intent.name)
                }
            }
            is PlaylistWholeContract.Intent.SelectDropDownOption -> {
                when(intent.option) {
                    DropDownOption.REMOVE -> {
                        viewModelScope.launch {
                            playlistUseCases.removePlaylist(intent.playlist.playlistId)
                        }
                    }
                    DropDownOption.RENAME -> {
                        _uiState.update { it.copy(
                            showRenameDialog = true,
                            playListToRename = intent.playlist
                        ) }
                    }
                    else -> {}
                }
            }

            is PlaylistWholeContract.Intent.RenamePlaylist -> {
                viewModelScope.launch {
                    playlistUseCases.renamePlaylist(intent.playlistId, intent.newName)
                    _uiState.update { it.copy(showRenameDialog = false, playListToRename = null) }
                }
            }
            is PlaylistWholeContract.Intent.ShowRenameDialog -> {
                _uiState.update { it.copy(showRenameDialog = false, playListToRename = intent.playlist) }
            }

            is PlaylistWholeContract.Intent.ShowAddDialog -> {
                _uiState.update { it.copy(showAddDialog = intent.show) }
            }
        }
    }

    fun processEvent(event: PlaylistWholeContract.Event) {

    }
}