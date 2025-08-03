package com.example.nhamngocduc.ui.playlist.whole

import com.example.nhamngocduc.domain.model.Playlist
import com.example.nhamngocduc.util.DropDownOption

object PlaylistWholeContract {
    data class State(
        val showDropDownMenu: Boolean = false,
        val showRenameDialog: Boolean = false,
        val showAddDialog: Boolean = false,
        val playListToRename: Playlist? = null
    )

    sealed interface Intent {
        data class SelectDropDownOption(val option: DropDownOption, val playlist: Playlist) : Intent
        data class RemovePlaylist(val playlist: Playlist) : Intent
        data class RenamePlaylist(val playlistId: Int, val newName: String) : Intent
        data class AddPlaylist(val name: String) : Intent
        data class ShowAddDialog(val show: Boolean) : Intent
        data class ShowRenameDialog(val show: Boolean, val playlist: Playlist? = null) : Intent
    }

    sealed interface Event {

    }
}