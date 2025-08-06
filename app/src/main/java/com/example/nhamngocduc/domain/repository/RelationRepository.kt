package com.example.nhamngocduc.domain.repository

import com.example.nhamngocduc.data.local.model.entity.relation.PlaylistMusicCrossRef

interface RelationRepository {
    suspend fun deletePlaylistMusicCrossRef(playlistId: Int, musicId: Long)
    suspend fun getPlaylistSongCrossRef(playlistId: Int, musicId: Long): PlaylistMusicCrossRef?
}