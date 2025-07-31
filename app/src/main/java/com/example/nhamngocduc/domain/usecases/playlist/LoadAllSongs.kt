package com.example.nhamngocduc.domain.usecases.playlist

import com.example.nhamngocduc.domain.model.Song
import com.example.nhamngocduc.domain.repository.AudioRepository

class LoadAllSongs(
    private val repository: AudioRepository
) {
    suspend operator fun invoke(): List<Song> {
        return repository.getAudioFiles()
    }
}