package com.example.nhamngocduc.domain.repository

import com.example.nhamngocduc.domain.model.Song
import com.example.nhamngocduc.domain.model.SongWithPlaylists
import kotlinx.coroutines.flow.Flow

interface MusicRepository {
    suspend fun getRemoteSongs() : Result<List<Song>>
    /**
     * Need to update because details of remote and local song
     * might be modified by user or server
     */
    suspend fun insertOrUpdateSong(song: Song): Long
    fun getAllSongs(): Flow<List<Song>>
    fun getSongWithPlaylists(songId: Long): Flow<SongWithPlaylists?>
    suspend fun getSongByRemoteSourceId(remoteSourceId: String): Song?
    suspend fun getSongByLocalStoreId(localStoreId: Long): Song?
}