package com.example.nhamngocduc.data.remote.mapper

import com.example.nhamngocduc.data.remote.model.artist.Artist
import com.example.nhamngocduc.domain.model.ArtistDomain
import com.example.nhamngocduc.util.EntityMapper

class ArtistMapper : EntityMapper<ArtistDomain, Artist> {
    override fun mapFromEntity(entity: Artist): ArtistDomain {
        val mediumImageUrl = entity.artistImage
            .firstOrNull{ it.size == "medium" }
            ?.text

        return ArtistDomain(
            artistImageUrl = mediumImageUrl,
            name = entity.name,
            url = entity.url
        )
    }

    override fun mapToEntity(domain: ArtistDomain): Artist {
        TODO("NOTHING")
    }
}