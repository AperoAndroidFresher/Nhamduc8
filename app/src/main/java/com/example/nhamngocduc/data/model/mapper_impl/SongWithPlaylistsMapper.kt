package com.example.nhamngocduc.data.model.mapper_impl

import com.example.nhamngocduc.data.model.entity.relation.PlaylistWithMusics
import com.example.nhamngocduc.domain.model.PlaylistWithSongs
import com.example.nhamngocduc.util.EntityMapper

class SongWithPlaylistsMapper(
    private val musicMapper: MusicMapper,
    private val playlistMapper: PlaylistMapper
) : EntityMapper<PlaylistWithSongs, PlaylistWithMusics> {
    override fun mapFromEntity(entity: PlaylistWithMusics): PlaylistWithSongs =
        PlaylistWithSongs(
            playlistMapper.mapFromEntity(entity.playlist),
            entity.musics.map { musicMapper.mapFromEntity(it) }
        )
    override fun mapToEntity(domain: PlaylistWithSongs): PlaylistWithMusics {
        TODO("Read only so no need")
    }
}