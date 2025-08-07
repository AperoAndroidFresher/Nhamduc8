package com.example.nhamngocduc.domain.usecases.library

import com.example.nhamngocduc.domain.model.Song
import com.example.nhamngocduc.domain.repository.AudioRepository

class LoadSongsFromLocal(
    private val repository: AudioRepository
) {
    suspend operator fun invoke(): List<Song> {
        return repository.getAudioFiles()
    }
}