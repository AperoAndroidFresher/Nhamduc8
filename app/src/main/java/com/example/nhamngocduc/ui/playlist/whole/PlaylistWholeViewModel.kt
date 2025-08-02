package com.example.nhamngocduc.ui.playlist.whole

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PlaylistWholeViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(PlaylistWholeContract.State())
    val uiState = _uiState.asStateFlow()

    fun processIntent(intent: PlaylistWholeContract.Intent) {
        when(intent) {
            is PlaylistWholeContract.Intent.removePlaylist -> TODO()
            is PlaylistWholeContract.Intent.showAddDialog -> {
                _uiState.update { it.copy(showAddDialog = intent.show) }
            }
            is PlaylistWholeContract.Intent.showRenameDialog -> {
                _uiState.update { it.copy(showRenameDialog = intent.show) }
            }
        }
    }

    fun processEvent(event: PlaylistWholeContract.Event) {

    }
}