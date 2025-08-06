package com.example.nhamngocduc.domain.usecases.library

import com.example.nhamngocduc.domain.repository.RelationRepository

class GetSongPlaylistLink(
    val relationRepository: RelationRepository,
) {
    suspend operator fun invoke(playlistId: Int, musicId: Long) =
        relationRepository.getPlaylistSongCrossRef(playlistId, musicId)
}