package com.example.nhamngocduc.ui.library

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nhamngocduc.domain.usecases.playlist.LibraryUseCases
import com.example.nhamngocduc.util.DropDownOption
import com.example.nhamngocduc.util.LibraryViewMode
import com.example.nhamngocduc.util.Tab
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class LibraryViewModel(
    private val libraryUseCases: LibraryUseCases
) : ViewModel() {
    private val _uiState = MutableStateFlow(LibraryContract.State())
    val uiState: StateFlow<LibraryContract.State> = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<LibraryContract.Event>()
    val event: SharedFlow<LibraryContract.Event> = _event.asSharedFlow()

    fun processIntent(intent: LibraryContract.Intent) {
        when (intent) {
            LibraryContract.Intent.ToggleViewMode -> _uiState.update { it.copy(
                viewMode = if (it.viewMode == LibraryViewMode.LOCAL) LibraryViewMode.REMOTE else LibraryViewMode.LOCAL
            )}

            LibraryContract.Intent.LoadSongs -> {
                if (_uiState.value.permissionGranted) {
                    loadAudioFiles()
                }
            }

            LibraryContract.Intent.GrantPermission -> {
                _uiState.update { it.copy(permissionGranted = true) }
            }

            is LibraryContract.Intent.SelectDropDownOption -> {
                when(intent.option) {
                    DropDownOption.ADD_TO_PLAYLIST -> {
                        _uiState.update { it.copy(showPlaylistDialog = true) }
                    }
                    DropDownOption.SHARE -> {}
                    else -> {}
                }
            }

            is LibraryContract.Intent.ShowPlaylistDialog -> _uiState.update { it.copy(showPlaylistDialog = intent.show) }

            is LibraryContract.Intent.SelectTab -> {
                when(intent.tab) {
                    Tab.LOCAL -> _uiState.update { it.copy(selectedTab = Tab.LOCAL) }
                    Tab.REMOTE -> _uiState.update { it.copy(selectedTab = Tab.REMOTE) }
                }
            }
        }
    }

    fun processEvent(event: LibraryContract.Event) {

    }

    private fun loadAudioFiles() {
        viewModelScope.launch {
            try {
                val loadedSongs = libraryUseCases.loadAllSongs()
                _uiState.update { it.copy(localSongs = loadedSongs) }
            } catch (e: Exception) {
                Log.e("PlaylistViewModel", "Error loading audio: ${e.message}", e)
            }
        }
    }
}