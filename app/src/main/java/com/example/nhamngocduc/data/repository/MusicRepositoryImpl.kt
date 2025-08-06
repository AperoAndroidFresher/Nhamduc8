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
    private val musicMapper: MusicMapper,
    private val songWithPlaylistsMapper: SongWithPlaylistsMapper
) : MusicRepository {

    override suspend fun insertOrUpdateSong(song: Song): Long {
        val existingSong = when {
            // This is local song
            song.localStoreId != null -> musicDao.getMusicByLocalStoreId(song.localStoreId)
            // This is remote song
            song.remoteSourceId != null -> musicDao.getMusicByRemoteSourceId(song.remoteSourceId)
            else -> null
        }

        if (existingSong != null) {
            val updatedMusicEntity = musicMapper.mapToEntity(song.copy(songId = existingSong.musicId))
            musicDao.insert(updatedMusicEntity) // REPLACE STRATEGY
            return existingSong.musicId
        } else {
            val newMusicEntity = musicMapper.mapToEntity(song)
            return musicDao.insert(newMusicEntity)
        }
    }

    override fun getAllSongs(): Flow<List<Song>> =
        musicDao.getAllMusics().map { list ->
            list.map { musicMapper.mapFromEntity(it) }
        }

    override fun getSongWithPlaylists(songId: Long): Flow<SongWithPlaylists?> =
       musicDao.getMusicWithPlaylists(songId).map { list ->
          list.firstOrNull()?.let {
               songWithPlaylistsMapper.mapFromEntity(it)
           }
       }

    override suspend fun getSongByRemoteSourceId(remoteSourceId: Long): Song? =
        musicDao.getMusicByRemoteSourceId(remoteSourceId)?.let {
            musicMapper.mapFromEntity(it)
        }

    override suspend fun getSongByLocalStoreId(localStoreId: Long): Song? =
        musicDao.getMusicByLocalStoreId(localStoreId)?.let {
            musicMapper.mapFromEntity(it)
        }


}