package com.example.nhamngocduc.data.repository

import com.example.nhamngocduc.data.local.model.entity.relation.PlaylistMusicCrossRef
import com.example.nhamngocduc.data.local.room.dao.PlaylistDao
import com.example.nhamngocduc.data.local.model.mapper.PlaylistMapper
import com.example.nhamngocduc.data.local.model.mapper.PlaylistWithSongsMapper
import com.example.nhamngocduc.data.local.room.dao.RelationDao
import com.example.nhamngocduc.domain.model.Playlist
import com.example.nhamngocduc.domain.model.PlaylistWithSongs
import com.example.nhamngocduc.domain.model.Song
import com.example.nhamngocduc.domain.repository.MusicRepository
import com.example.nhamngocduc.domain.repository.PlaylistRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PlaylistRepositoryImpl(
    private val playlistDao: PlaylistDao,
    private val playlistMapper: PlaylistMapper,
    private val playlistWithSongsMapper: PlaylistWithSongsMapper,
    private val musicRepository: MusicRepository,
    private val relationDao: RelationDao
) : PlaylistRepository {

    override fun getAllPlaylists(username: String): Flow<List<Playlist>> =
        playlistDao.getAllPlaylists(username).map { list ->
            list.map { playlistMapper.mapFromEntity(it) }
        }

    override fun getPlaylist(id: Int): Flow<Playlist?> =
        playlistDao.getPlaylist(id).map { playlistEntity ->
            playlistEntity?.let { playlistMapper.mapFromEntity(it) }
        }

    override suspend fun deletePlaylist(id: Int) =
        playlistDao.deletePlaylist(id)

    override suspend fun insertPlaylist(playlist: Playlist) =
        playlistDao.insertPlaylist(playlistMapper.mapToEntity(playlist))

    override suspend fun renamePlaylist(id: Int, newName: String) =
        playlistDao.renamePlaylist(id, newName)

    override fun getPlaylistWithSongs(id: Int): Flow<PlaylistWithSongs?> =
        playlistDao.getPlaylistWithMusics(id).map { list ->
            list.firstOrNull()?.let {
                playlistWithSongsMapper.mapFromEntity(it)
            }
        }

    override suspend fun addSongToPlaylist(playlist: Playlist, song: Song) {
        val existingMusicId = musicRepository.insertOrUpdateSong(song)

        val crossRef = PlaylistMusicCrossRef(
            playlist.playlistId,
            musicId = existingMusicId
        )

        relationDao.insertPlaylistMusicCrossRef(crossRef)
    }
}