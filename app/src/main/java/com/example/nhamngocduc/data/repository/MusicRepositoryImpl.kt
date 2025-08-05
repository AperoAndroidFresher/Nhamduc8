package com.example.nhamngocduc.data.repository

import com.example.nhamngocduc.data.local.room.dao.MusicDao
import com.example.nhamngocduc.data.local.model.mapper.MusicMapper
import com.example.nhamngocduc.data.local.model.mapper.SongWithPlaylistsMapper
import com.example.nhamngocduc.domain.model.Song
import com.example.nhamngocduc.domain.model.SongWithPlaylists
import com.example.nhamngocduc.domain.repository.MusicRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MusicRepositoryImpl(
    private val musicDao: MusicDao,
    private val songMapper: MusicMapper,
    private val songWithPlaylistsMapper: SongWithPlaylistsMapper
) : MusicRepository {
    override suspend fun insert(song: Song) =
        musicDao.insert(songMapper.mapToEntity(song))

    override fun getAllSongs(): Flow<List<Song>> =
        musicDao.getAllMusics().map { list ->
            list.map { songMapper.mapFromEntity(it) }
        }

    override fun getMusicWithPlaylists(songId: Long): Flow<SongWithPlaylists?> =
       musicDao.getMusicWithPlaylists(songId).map { list ->
          list.firstOrNull()?.let {
               songWithPlaylistsMapper.mapFromEntity(it)
           }
       }

}