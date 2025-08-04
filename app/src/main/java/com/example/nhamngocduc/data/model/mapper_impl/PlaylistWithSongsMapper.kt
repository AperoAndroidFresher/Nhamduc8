package com.example.nhamngocduc.data.model.mapper_impl

import com.example.nhamngocduc.data.model.entity.relation.PlaylistWithMusics
import com.example.nhamngocduc.domain.model.PlaylistWithSongs
import com.example.nhamngocduc.util.EntityMapper

class PlaylistWithSongsMapper(
    private val playlistMapper: PlaylistMapper,
    private val musicMapper: MusicMapper
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