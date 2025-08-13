package com.example.nhamngocduc.ui.playlist.playlist_detail

import android.content.Context
import com.example.nhamngocduc.domain.model.Playlist
import com.example.nhamngocduc.domain.model.Song
import com.example.nhamngocduc.util.DropDownOption
import com.example.nhamngocduc.util.ViewMode

object PlaylistDetailContract {
    data class State(
        val playlist: Playlist? = null,
        val viewMode: ViewMode = ViewMode.LIST,
        val isSorted: Boolean = false,
        val songsList: List<Song> = emptyList(),
        val currentPlayingSong: Song? = null,
        val startIndex: Int? = null
    )

    sealed interface Intent {
        data object ToggleViewMode: Intent
        data object ToggleSort: Intent
        data class SelectDropDownOption(val option: DropDownOption, val song: Song) : Intent
        data class PlaySong(val clickedSong: Song) : Intent

        data class LoadPlaylistDetail(val playlistId: Int) : Intent
    }
}