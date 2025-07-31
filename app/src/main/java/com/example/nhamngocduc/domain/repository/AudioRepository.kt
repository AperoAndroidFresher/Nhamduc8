package com.example.nhamngocduc.domain.repository

import com.example.nhamngocduc.domain.model.Song

interface AudioRepository {
    suspend fun getAudioFiles() : List<Song>
}