package com.example.nhamngocduc.ui.library

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nhamngocduc.domain.manager.SessionManager
import com.example.nhamngocduc.domain.model.Playlist
import com.example.nhamngocduc.domain.model.Song
import com.example.nhamngocduc.domain.usecases.library.LibraryUseCases
import com.example.nhamngocduc.domain.usecases.playlist.PlaylistUseCases
import com.example.nhamngocduc.util.DropDownOption
import com.example.nhamngocduc.util.LibraryViewMode
import com.example.nhamngocduc.util.Tab
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class LibraryViewModel(
    private val libraryUseCases: LibraryUseCases,
    private val playlistUseCases: PlaylistUseCases,
    private val sessionManager: SessionManager,
) : ViewModel() {
    private val _uiState = MutableStateFlow(LibraryContract.State())
    val uiState: StateFlow<LibraryContract.State> = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<LibraryContract.Event>()
    val event: SharedFlow<LibraryContract.Event> = _event.asSharedFlow()

    private val username = sessionManager.currentUsername

    private val scope = viewModelScope

    val playlist = playlistUseCases.getAllPlaylists(username!!)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )

    fun processIntent(intent: LibraryContract.Intent) {
        when (intent) {
            LibraryContract.Intent.ToggleViewMode -> toggleViewMode()

            LibraryContract.Intent.LoadSongs -> {
                // Might fix in the following updates
                if (_uiState.value.permissionGranted) {
                    loadAudioFiles()
                }
            }

            LibraryContract.Intent.GrantPermission -> _uiState.update { it.copy(permissionGranted = true) }

            is LibraryContract.Intent.SelectDropDownOption -> selectOption(intent.option, intent.song)

            is LibraryContract.Intent.ShowPlaylistDialog -> _uiState.update { it.copy(showPlaylistDialog = intent.show) }

            is LibraryContract.Intent.SelectTab -> selectTab(intent.tab)

            is LibraryContract.Intent.AddSongToPlaylist -> addSongToPlaylist(intent.playlist, _uiState.value.selectedSong!!)
            is LibraryContract.Intent.ResetSelectedSong -> _uiState.update { it.copy(selectedSong = null) }
        }
    }

    fun processEvent(event: LibraryContract.Event) {
        when(event) {
            LibraryContract.Event.NavigateToPlaylistWholeScreen -> viewModelScope.launch { _event.emit(event) }
        }
    }

    private fun toggleViewMode() {
        _uiState.update { it.copy(
            viewMode =
                if (it.viewMode == LibraryViewMode.LOCAL) LibraryViewMode.REMOTE
                else LibraryViewMode.LOCAL
        )}
    }

    private fun selectTab(tab: Tab) {
        when(tab) {
            Tab.LOCAL -> _uiState.update { it.copy(selectedTab = Tab.LOCAL) }
            Tab.REMOTE -> _uiState.update { it.copy(selectedTab = Tab.REMOTE) }
        }
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

    private fun selectOption(option: DropDownOption, song: Song) {
        when(option) {
            DropDownOption.ADD_TO_PLAYLIST -> _uiState.update { it.copy(showPlaylistDialog = true, selectedSong = song) }
            DropDownOption.REMOVE -> {}
            else -> {}
        }
    }

    private fun addSongToPlaylist(playlist: Playlist, song: Song) {
        scope.launch {
            playlistUseCases.addSongToPlaylist(playlist, song)
        }
    }
}