package com.example.nhamngocduc.domain.repository

import com.example.nhamngocduc.domain.model.Playlist
import com.example.nhamngocduc.domain.model.PlaylistWithSongs
import com.example.nhamngocduc.domain.model.Song
import kotlinx.coroutines.flow.Flow

interface PlaylistRepository {
    fun getAllPlaylists(username: String): Flow<List<Playlist>>
    fun getPlaylist(id : Int): Flow<Playlist?>
    suspend fun deletePlaylist(id: Int)
    suspend fun insertPlaylist(playlist: Playlist)
    suspend fun renamePlaylist(id: Int, newName: String)
    fun getPlaylistWithSongs(id: Int): Flow<PlaylistWithSongs?>

    /**
     * Check if the song is already exists in music
     * If yes, create a link between music and playlist
     */
    suspend fun addSongToPlaylist(playlist: Playlist, song: Song)
}