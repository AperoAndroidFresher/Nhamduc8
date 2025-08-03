package com.example.nhamngocduc.data.repository

import com.example.nhamngocduc.domain.model.Playlist
import com.example.nhamngocduc.domain.model.Song
import com.example.nhamngocduc.domain.repository.PlaylistRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class PlaylistRepositoryImpl : PlaylistRepository {
    private val _fakePlaylistList = MutableStateFlow(mutableListOf<Playlist>())
    private var nextId = 0
    override fun getAllPlaylists(): Flow<List<Playlist>> {
        return _fakePlaylistList.asStateFlow()
    }

    override fun getPlaylist(id: Int): Flow<Playlist?> {
        return _fakePlaylistList.asStateFlow().map { it.find { playlist -> playlist.id == id } }
    }

    override fun addNewPlaylist(name: String) {
        val newPlaylist = Playlist(id = nextId++, playlistName = name)
        _fakePlaylistList.update {
            val updatedList = it
            it.add(newPlaylist)
            updatedList
        }
    }

    override fun removePlaylist(id: Int) {
        _fakePlaylistList.update { mutableList ->
            mutableList.filter { it.id != id}.toMutableList()
        }
    }

    override fun renamePlaylist(id: Int, newName: String) {
        _fakePlaylistList.update { currentList ->
            currentList.map {
                if (it.id == id) {
                    it.copy(playlistName = newName)
                } else {
                    it
                }
            }.toMutableList()
        }
    }

    override fun addSongToPlaylist(playlistId: Int, song: Song) {
        _fakePlaylistList.update { currentPlaylist ->
            currentPlaylist.map { playlist ->
                if (playlist.id == playlistId) {
                    val updatedSongs = playlist.songsList.toMutableList()
                    updatedSongs.add(song)
                    playlist.copy(songsList = updatedSongs)
                } else {
                    playlist
                }
            }.toMutableList()
        }
    }

    override fun removeSongFromPlaylist(playListId: Int, songId: Long) {
        _fakePlaylistList.update { currentPlaylist ->
            currentPlaylist.map { playlist ->
                if (playlist.id == playListId) {
                    val updatedSongs = playlist.songsList.toMutableList()
                    updatedSongs.removeAll { it.id == songId }
                    playlist.copy(songsList = updatedSongs)
                } else {
                    playlist
                }
            }.toMutableList()
        }
    }
}