package com.example.nhamngocduc.data.remote.mapper

import com.example.nhamngocduc.data.remote.model.album.Album
import com.example.nhamngocduc.domain.model.AlbumDomain
import com.example.nhamngocduc.util.EntityMapper

class AlbumMapper : EntityMapper<AlbumDomain, Album> {
    override fun mapFromEntity(entity: Album): AlbumDomain {
        val mediumImageUrl = entity.albumImage
            ?.firstOrNull { image ->
                image.size == "medium" && !image.text.isNullOrEmpty()
            }
            ?.text

        return AlbumDomain(
            artistName = entity.albumArtist?.name ?: "Unknown Artist",
            albumImageUrl = mediumImageUrl,
            name = entity.name,
            url = entity.url
        )
    }

    override fun mapToEntity(domain: AlbumDomain): Album {
        TODO("NOTHING")
    }
}