package com.example.nhamngocduc.domain.usecases.music

import com.example.nhamngocduc.domain.model.Song
import com.example.nhamngocduc.domain.repository.MusicRepository
import kotlinx.coroutines.flow.Flow

class GetAllSongs(
    private val musicRepository: MusicRepository
) {
    operator fun invoke(): Flow<List<Song>> =
        musicRepository.getAllSongs()
}