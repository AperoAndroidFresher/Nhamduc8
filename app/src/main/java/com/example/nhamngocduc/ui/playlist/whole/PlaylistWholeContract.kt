package com.example.nhamngocduc.ui.playlist.whole

import com.example.nhamngocduc.domain.model.Playlist

object PlaylistWholeContract {
    data class State(
        val showAddDialog: Boolean = false,
        val showRenameDialog: Boolean = false
    )

    sealed interface Intent {
        data class showAddDialog(val show: Boolean) : Intent
        data class showRenameDialog(val show: Boolean) : Intent
        data class removePlaylist(val playlist: Playlist) : Intent
    }

    sealed interface Event {

    }
}