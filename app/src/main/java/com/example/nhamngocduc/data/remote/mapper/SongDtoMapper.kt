package com.example.nhamngocduc.data.remote.mapper

import com.example.nhamngocduc.data.remote.model.SongDto
import com.example.nhamngocduc.domain.model.Song
import com.example.nhamngocduc.util.EntityMapper

class SongDtoMapper : EntityMapper<Song, SongDto> {
    override fun mapFromEntity(entity: SongDto): Song = Song(
        title = entity.title,
        artist = entity.artist,
        genre = entity.genre,
        duration = entity.duration,
        contentUri = null
    )

    override fun mapToEntity(domain: Song): SongDto {
        TODO("Not yet implemented")
    }

}