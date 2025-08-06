package com.example.nhamngocduc.ui.playlist.whole

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nhamngocduc.domain.manager.SessionManager
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
    private val playlistUseCases: PlaylistUseCases,
    private val sessionManager: SessionManager
) : ViewModel() {
    private val _uiState = MutableStateFlow(PlaylistWholeContract.State())
    val uiState = _uiState.asStateFlow()

    private val scope = viewModelScope

    private val username = sessionManager.currentUsername

    val playlists: StateFlow<List<Playlist>> = playlistUseCases.getAllPlaylists(username!!)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )

    fun processIntent(intent: PlaylistWholeContract.Intent) {
        when(intent) {
            is PlaylistWholeContract.Intent.AddPlaylist -> addNewPlaylist(intent.playlistName)
            is PlaylistWholeContract.Intent.SelectDropDownOption -> executeDropDownOption(intent.option, intent.playlist)

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

    private fun executeDropDownOption(option: DropDownOption, playlist: Playlist) {
        when(option) {
            DropDownOption.REMOVE -> {
                removePlaylist(playlist)
            }
            DropDownOption.RENAME -> {
                _uiState.update { it.copy(
                    showRenameDialog = true,
                    playListToRename = playlist
                ) }
            }
            else -> {}
        }
    }
    private fun removePlaylist(playlist: Playlist) {
        scope.launch {
            playlistUseCases.removePlaylist(playlist.playlistId)
        }
    }

    private fun addNewPlaylist(playlistName: String) {
        scope.launch {
            playlistUseCases.addNewPlaylist(Playlist(
                playlistName = playlistName,
                username = username!!
            ))
        }
    }
}