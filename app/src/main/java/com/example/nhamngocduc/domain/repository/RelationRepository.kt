package com.example.nhamngocduc.domain.repository

import com.example.nhamngocduc.data.model.entity.relation.PlaylistMusicCrossRef

interface RelationRepository {
    suspend fun insertPlaylistMusicCrossRef(crossRef: PlaylistMusicCrossRef)
    suspend fun deletePlaylistMusicCrossRef(playlistId: Int, musicId: Long)
}