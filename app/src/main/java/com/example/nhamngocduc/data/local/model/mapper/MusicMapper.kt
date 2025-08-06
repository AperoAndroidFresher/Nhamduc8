package com.example.nhamngocduc.data.local.model.mapper

import com.example.nhamngocduc.data.local.model.entity.MusicEntity
import com.example.nhamngocduc.domain.model.Song
import com.example.nhamngocduc.util.EntityMapper

class MusicMapper : EntityMapper<Song, MusicEntity> {
    override fun mapFromEntity(entity: MusicEntity): Song =
        Song(
            entity.musicId,
            entity.localStoreId,
            entity.remoteSourceId,
            entity.title,
            entity.artist,
            "",
            entity.duration,
            entity.contentUri
        )

    override fun mapToEntity(domain: Song): MusicEntity =
        MusicEntity(
            domain.songId,
            domain.localStoreId,
            domain.remoteSourceId,
            domain.title,
            domain.artist,
            domain.duration,
            domain.contentUri
        )

}