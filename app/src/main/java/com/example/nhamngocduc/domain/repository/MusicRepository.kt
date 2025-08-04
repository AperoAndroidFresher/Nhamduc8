package com.example.nhamngocduc.domain.repository

import com.example.nhamngocduc.data.model.entity.MusicEntity
import com.example.nhamngocduc.data.model.entity.relation.MusicWithPlaylists
import kotlinx.coroutines.flow.Flow

interface MusicRepository {
    suspend fun insert(music: MusicEntity)
    suspend fun getAllMusics(): List<MusicEntity>
    fun getMusicWithPlaylists(musicId: Long): Flow<List<MusicWithPlaylists>>
}