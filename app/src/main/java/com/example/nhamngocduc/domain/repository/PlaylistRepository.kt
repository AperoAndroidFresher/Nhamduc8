package com.example.nhamngocduc.domain.repository

import com.example.nhamngocduc.domain.model.Playlist
import com.example.nhamngocduc.domain.model.Song
import kotlinx.coroutines.flow.Flow

interface PlaylistRepository {
    fun getAllPlaylists(): Flow<List<Playlist>>
    fun getPlaylist(id : Int): Flow<Playlist?>
    fun removePlaylist(id: Int)
    fun addNewPlaylist(name: String)
    fun renamePlaylist(id: Int, newName: String)
    fun addSongToPlaylist(playlistId: Int, song: Song)
    fun removeSongFromPlaylist(playListId: Int, songId: Long)
}