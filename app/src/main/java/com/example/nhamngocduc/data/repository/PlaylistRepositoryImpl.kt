package com.example.nhamngocduc.data.repository


import com.example.nhamngocduc.data.local.room.dao.PlaylistDao
import com.example.nhamngocduc.data.model.entity.PlaylistEntity
import com.example.nhamngocduc.data.model.entity.relation.PlaylistWithMusics
import com.example.nhamngocduc.domain.repository.PlaylistRepository
import kotlinx.coroutines.flow.Flow

class PlaylistRepositoryImpl(
    private val playlistDao: PlaylistDao
) : PlaylistRepository {
    override fun getAllPlaylists(): Flow<List<PlaylistEntity>> =
        playlistDao.getAllPlaylists()

    override fun getPlaylist(id: Int): Flow<PlaylistEntity?> =
        playlistDao.getPlaylist(id)

    override suspend fun deletePlaylist(id: Int) =
        playlistDao.deletePlaylist(id)

    override suspend fun insertPlaylist(playlistEntity: PlaylistEntity) =
        playlistDao.insertPlaylist(playlistEntity)

    override suspend fun renamePlaylist(id: Int, newName: String) =
        playlistDao.renamePlaylist(id, newName)

    override suspend fun getMusicWithPlaylists(id: Int): Flow<List<PlaylistWithMusics>> =
        playlistDao.getPlaylistWithMusics(id)
}