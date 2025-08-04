package com.example.nhamngocduc.data.model.mapper_impl

import com.example.nhamngocduc.data.model.entity.MusicEntity
import com.example.nhamngocduc.domain.model.Song
import com.example.nhamngocduc.util.EntityMapper

class MusicMapper : EntityMapper<Song, MusicEntity> {
    override fun mapFromEntity(entity: MusicEntity): Song =
        Song(
            entity.musicId,
            entity.title,
            entity.artist,
            entity.duration,
            entity.contentUri
        )

    override fun mapToEntity(domain: Song): MusicEntity =
        MusicEntity(
            domain.songId,
            domain.title,
            domain.artist,
            domain.duration,
            domain.contentUri
        )

}