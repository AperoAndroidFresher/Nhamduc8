package com.example.nhamngocduc.data.repository

import com.example.nhamngocduc.data.local.room.dao.RelationDao
import com.example.nhamngocduc.data.local.model.entity.relation.PlaylistMusicCrossRef
import com.example.nhamngocduc.domain.repository.RelationRepository

class RelationRepositoryImpl(
    private val relationDao: RelationDao
) : RelationRepository {
    override suspend fun insertPlaylistMusicCrossRef(crossRef: PlaylistMusicCrossRef) =
        relationDao.insertPlaylistMusicCrossRef(crossRef)

    override suspend fun deletePlaylistMusicCrossRef(playlistId: Int, musicId: Long) =
        relationDao.deletePlaylistMusicCrossRef(playlistId, musicId)
}