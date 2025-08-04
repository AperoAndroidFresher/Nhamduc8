package com.example.nhamngocduc.domain.repository

import com.example.nhamngocduc.data.model.entity.PlaylistEntity
import com.example.nhamngocduc.data.model.entity.relation.PlaylistWithMusics
import kotlinx.coroutines.flow.Flow

interface PlaylistRepository {
    fun getAllPlaylists(): Flow<List<PlaylistEntity>>
    fun getPlaylist(id : Int): Flow<PlaylistEntity?>
    suspend fun deletePlaylist(id: Int)
    suspend fun insertPlaylist(playlistEntity: PlaylistEntity)
    suspend fun renamePlaylist(id: Int, newName: String)
    suspend fun getMusicWithPlaylists(id: Int): Flow<List<PlaylistWithMusics>>
}