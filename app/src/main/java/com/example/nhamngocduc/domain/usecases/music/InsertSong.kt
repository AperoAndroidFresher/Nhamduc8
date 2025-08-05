package com.example.nhamngocduc.domain.usecases.music

import com.example.nhamngocduc.domain.model.Song
import com.example.nhamngocduc.domain.repository.MusicRepository

class InsertSong(
    private val musicRepository: MusicRepository
) {
    suspend operator fun invoke(song: Song) {
        musicRepository.insert(song)
    }
}