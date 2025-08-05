package com.example.nhamngocduc.domain.repository

import com.example.nhamngocduc.domain.model.Playlist
import com.example.nhamngocduc.domain.model.PlaylistWithSongs
import kotlinx.coroutines.flow.Flow

interface PlaylistRepository {
    fun getAllPlaylists(): Flow<List<Playlist>>
    fun getPlaylist(id : Int): Flow<Playlist?>
    suspend fun deletePlaylist(id: Int)
    suspend fun insertPlaylist(playlist: Playlist)
    suspend fun renamePlaylist(id: Int, newName: String)
    fun getPlaylistWithSongs(id: Int): Flow<PlaylistWithSongs?>
}