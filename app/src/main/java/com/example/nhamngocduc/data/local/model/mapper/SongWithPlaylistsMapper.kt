package com.example.nhamngocduc.data.local.model.mapper

import com.example.nhamngocduc.data.local.model.entity.relation.MusicWithPlaylists
import com.example.nhamngocduc.domain.model.SongWithPlaylists
import com.example.nhamngocduc.util.EntityMapper

class SongWithPlaylistsMapper(
    private val playlistMapper: PlaylistMapper,
    private val musicMapper: MusicMapper
) : EntityMapper<SongWithPlaylists, MusicWithPlaylists> {
    override fun mapFromEntity(entity: MusicWithPlaylists): SongWithPlaylists =
        SongWithPlaylists(
            musicMapper.mapFromEntity(entity.music),
            entity.playlists.map { playlistMapper.mapFromEntity(it) }
        )

    override fun mapToEntity(domain: SongWithPlaylists): MusicWithPlaylists {
        TODO("READ ONLY SO NO NEED")
    }

}