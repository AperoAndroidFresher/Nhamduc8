package com.example.nhamngocduc.ui.playlist.details

import androidx.lifecycle.ViewModel
import com.example.nhamngocduc.util.DropDownOption
import com.example.nhamngocduc.util.ViewMode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PlaylistDetailViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(PlaylistDetailContract.State())
    val uiState: StateFlow<PlaylistDetailContract.State> = _uiState.asStateFlow()

    fun processIntent(intent: PlaylistDetailContract.Intent) {
        when(intent) {
            is PlaylistDetailContract.Intent.ToggleSort -> _uiState.update { it.copy(isSorted = !it.isSorted) }
            is PlaylistDetailContract.Intent.ToggleViewMode -> _uiState.update { it.copy(
                viewMode = if (it.viewMode == ViewMode.LIST) ViewMode.GRID else ViewMode.LIST
            ) }


            is PlaylistDetailContract.Intent.SelectDropDownOption -> {
                when (intent.option) {
                    DropDownOption.REMOVE -> {
                        val updatedSongs = _uiState.value.songsList.filter { it.id != intent.song.id }
                        _uiState.update { it.copy(songsList = updatedSongs) }
                    }
                    DropDownOption.SHARE -> {}
                    else -> {}
                }
            }
        }
    }

    fun processEvent(event: PlaylistDetailContract.Event) {

    }
}