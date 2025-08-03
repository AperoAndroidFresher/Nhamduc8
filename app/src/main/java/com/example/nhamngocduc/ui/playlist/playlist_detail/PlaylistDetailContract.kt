package com.example.nhamngocduc.ui.playlist.playlist_detail

import com.example.nhamngocduc.domain.model.Song
import com.example.nhamngocduc.util.DropDownOption
import com.example.nhamngocduc.util.ViewMode

object PlaylistDetailContract {
    data class State(
        val viewMode: ViewMode = ViewMode.LIST,
        val isSorted: Boolean = false,
        val songsList: List<Song> = emptyList(),
    )

    sealed interface Intent {
        data object ToggleViewMode: Intent
        data object ToggleSort: Intent
        data class SelectDropDownOption(val option: DropDownOption, val song: Song) : Intent
    }

    sealed interface Event {
        data class NavigateToPlaylistDetails(val songId: Int)
    }
}