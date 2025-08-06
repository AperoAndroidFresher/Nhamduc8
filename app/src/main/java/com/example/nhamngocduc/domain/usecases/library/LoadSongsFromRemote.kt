package com.example.nhamngocduc.domain.usecases.library

import com.example.nhamngocduc.domain.model.Song
import com.example.nhamngocduc.domain.repository.SongNetworkRepository

class LoadSongsFromRemote(
    private val songNetworkRepository: SongNetworkRepository
) {
    suspend operator fun invoke(): Result<List<Song>> =
        songNetworkRepository.getSongsResult()
}