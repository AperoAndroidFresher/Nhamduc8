package com.example.nhamngocduc.domain.usecases.music

import com.example.nhamngocduc.domain.repository.MusicRepository

class GetSongByRemoteId(
    private val musicRepository: MusicRepository
) {
    suspend operator fun invoke(remoteId: Long) =
        musicRepository.getSongByRemoteSourceId(remoteId)
}