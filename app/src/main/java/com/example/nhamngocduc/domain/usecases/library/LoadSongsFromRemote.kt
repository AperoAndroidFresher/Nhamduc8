package com.example.nhamngocduc.domain.usecases.library

import com.example.nhamngocduc.domain.model.Song
import com.example.nhamngocduc.domain.repository.MusicRepository

class LoadSongsFromRemote(
    private val musicRepository: MusicRepository
) {
    suspend operator fun invoke(): Result<List<Song>> =
        musicRepository.getRemoteSongs()
}