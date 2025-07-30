package com.example.nhamngocduc.ui.playlist

import androidx.lifecycle.ViewModel
import com.example.nhamngocduc.util.DropDownOption
import com.example.nhamngocduc.util.SongList
import com.example.nhamngocduc.util.ViewMode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PlaylistViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(PlaylistContract.State())
    val uiState: StateFlow<PlaylistContract.State> = _uiState.asStateFlow()

    init {
        _uiState.value = _uiState.value.copy(songsList = SongList.songsList)
    }

    fun processIntent(intent: PlaylistContract.Intent) {
        when(intent) {
            is PlaylistContract.Intent.ToggleSort -> _uiState.update { it.copy(isSorted = !it.isSorted) }
            is PlaylistContract.Intent.ToggleViewMode -> _uiState.update { it.copy(
                viewMode = if (it.viewMode == ViewMode.LIST) ViewMode.GRID else ViewMode.LIST
            ) }
            is PlaylistContract.Intent.SelectDropDownOption -> {
                when (intent.option) {
                    DropDownOption.REMOVE -> {
                        val updatedSongs = _uiState.value.songsList.filter { it.id != intent.song.id }
                        _uiState.update { it.copy(songsList = updatedSongs) }
                    }
                    DropDownOption.SHARE -> {}
                }
            }
        }
    }

    fun processEvent(event: PlaylistContract.Event) {

    }
}