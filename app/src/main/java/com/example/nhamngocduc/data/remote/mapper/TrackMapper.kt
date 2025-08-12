package com.example.nhamngocduc.data.remote.mapper

import com.example.nhamngocduc.data.remote.model.track.Track
import com.example.nhamngocduc.domain.model.TrackDomain
import com.example.nhamngocduc.util.EntityMapper

class TrackMapper : EntityMapper<TrackDomain, Track> {
    override fun mapFromEntity(entity: Track): TrackDomain {
        val mediumImageUrl = entity.trackImage
            .firstOrNull{ it.size == "medium" }
            ?.text

        return TrackDomain(
            artistName = entity.trackArtist.name,
            trackImageUrl = mediumImageUrl,
            playcount = entity.playcount,
            url = entity.url,
            name = entity.trackName
        )
    }

    override fun mapToEntity(domain: TrackDomain): Track {
        throw UnsupportedOperationException("Mapping from Domain to Entity is not supported for Track.")
    }
}