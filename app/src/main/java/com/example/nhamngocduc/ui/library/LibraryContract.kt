package com.example.nhamngocduc.ui.library

import com.example.nhamngocduc.domain.model.Playlist
import com.example.nhamngocduc.domain.model.Song
import com.example.nhamngocduc.util.DropDownOption
import com.example.nhamngocduc.util.LibraryViewMode
import com.example.nhamngocduc.util.Tab

object LibraryContract {
    data class State(
        val localSongs: List<Song> = emptyList(),
        val remoteSongs: List<Song> = emptyList(),
        val viewMode: LibraryViewMode = LibraryViewMode.LOCAL,
        val selectedSong: Song? = null,
        val permissionGranted: Boolean = false,
        val showPlaylistDialog: Boolean = false,
        val selectedTab: Tab = Tab.LOCAL
    )

    sealed interface Intent {
        data object ToggleViewMode : Intent
        data object LoadSongs : Intent
        data object GrantPermission : Intent
        data class SelectDropDownOption(val option: DropDownOption, val song: Song) : Intent
        data class ShowPlaylistDialog(val show: Boolean) : Intent
        data class SelectTab(val tab: Tab) : Intent
        data class AddSongToPlaylist(val playlist: Playlist) : Intent
        data object ResetSelectedSong : Intent
    }

    sealed interface Event {
        data object NavigateToPlaylistWholeScreen : Event
    }
}