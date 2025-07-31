package com.example.nhamngocduc.ui.playlist

import com.example.nhamngocduc.domain.model.Song
import com.example.nhamngocduc.util.DropDownOption
import com.example.nhamngocduc.util.ViewMode

object PlaylistContract {
    data class State(
        val viewMode: ViewMode = ViewMode.LIST,
        val isSorted: Boolean = false,
        val songsList: List<Song> = emptyList(),
        val permissionGranted: Boolean = false
    )

    sealed interface Intent {
        data object ToggleViewMode: Intent
        data object ToggleSort: Intent
        data class SelectDropDownOption(val option: DropDownOption, val song: Song) : Intent
        data object LoadSongs: Intent
        data object GrantPermission : Intent
    }

    sealed interface Event {
        data class NavigateToSongDetails(val songId: Int)
    }
}