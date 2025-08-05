package com.example.nhamngocduc.domain.repository

import com.example.nhamngocduc.domain.model.Song
import com.example.nhamngocduc.domain.model.SongWithPlaylists
import kotlinx.coroutines.flow.Flow

interface MusicRepository {
    suspend fun insert(song: Song)
    fun getAllSongs(): Flow<List<Song>>
    fun getMusicWithPlaylists(songId: Long): Flow<SongWithPlaylists?>
}