package com.example.nhamngocduc.domain.usecases.music

import com.example.nhamngocduc.domain.repository.MusicRepository

class GetSongByLocalId(
    private val musicRepository: MusicRepository
) {
    suspend operator fun invoke(localStoreId: Long) =
        musicRepository.getSongByLocalStoreId(localStoreId)
}