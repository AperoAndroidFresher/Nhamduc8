package com.example.nhamngocduc.data.repository

import com.example.nhamngocduc.data.local.room.dao.MusicDao
import com.example.nhamngocduc.data.model.entity.MusicEntity
import com.example.nhamngocduc.data.model.entity.relation.MusicWithPlaylists
import com.example.nhamngocduc.domain.repository.MusicRepository
import kotlinx.coroutines.flow.Flow

class MusicRepositoryImpl(
    private val musicDao: MusicDao
) : MusicRepository {
    override suspend fun insert(music: MusicEntity) =
        musicDao.insert(music)

    override suspend fun getAllMusics(): List<MusicEntity> =
        musicDao.getAllMusics()

    override fun getMusicWithPlaylists(musicId: Long): Flow<List<MusicWithPlaylists>> =
        musicDao.getMusicWithPlaylists(musicId)

}