package com.example.nhamngocduc.ui.playlist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nhamngocduc.domain.repository.AudioRepository
import com.example.nhamngocduc.util.DropDownOption
import com.example.nhamngocduc.util.ViewMode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PlaylistViewModel(
    private val audioRepository: AudioRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(PlaylistContract.State())
    val uiState: StateFlow<PlaylistContract.State> = _uiState.asStateFlow()

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
            is PlaylistContract.Intent.LoadSongs -> {
                if (_uiState.value.permissionGranted) {
                    loadAudioFiles()
                }
            }
            is PlaylistContract.Intent.GrantPermission -> {
                _uiState.update { it.copy(permissionGranted = true) }
                loadAudioFiles()
            }
        }
    }

    fun processEvent(event: PlaylistContract.Event) {

    }

    private fun loadAudioFiles() {
        viewModelScope.launch {
            try {
                val loadedSongs = audioRepository.getAudioFiles()
                _uiState.update { it.copy(songsList = loadedSongs) }
            } catch (e: Exception) {
                Log.e("PlaylistViewModel", "Error loading audio: ${e.message}", e)
            }
        }
    }
}