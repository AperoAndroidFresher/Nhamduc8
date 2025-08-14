package com.example.nhamngocduc.data.remote.mapper

import android.net.Uri
import com.example.nhamngocduc.data.remote.model.SongDto
import com.example.nhamngocduc.domain.model.Song
import com.example.nhamngocduc.util.EntityMapper

class SongDtoMapper : EntityMapper<Song, SongDto> {
    override fun mapFromEntity(entity: SongDto): Song = Song(
        remoteSourceId = entity.path,
        title = entity.title,
        artist = entity.artist,
        duration = entity.duration.toLongOrNull() ?: 0L,
        contentUri = entity.path?.let { Uri.parse(it) }
    )

    override fun mapToEntity(domain: Song): SongDto {
        TODO("Just get and no post so no need")
    }

}