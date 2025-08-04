package com.example.nhamngocduc.data.model.mapper_impl

import com.example.nhamngocduc.data.model.entity.PlaylistEntity
import com.example.nhamngocduc.domain.model.Playlist
import com.example.nhamngocduc.util.EntityMapper

class PlaylistMapper : EntityMapper<Playlist, PlaylistEntity> {
    override fun mapFromEntity(entity: PlaylistEntity): Playlist =
        Playlist(
            entity.playlistId,
            entity.playlistName,
            entity.image,
            entity.username
        )

    override fun mapToEntity(domain: Playlist): PlaylistEntity =
        PlaylistEntity(
            domain.playlistId,
            domain.playlistName,
            domain.image,
            domain.username
        )

}