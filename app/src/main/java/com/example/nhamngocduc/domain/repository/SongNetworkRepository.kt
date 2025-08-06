package com.example.nhamngocduc.domain.repository

import com.example.nhamngocduc.domain.model.Song

interface SongNetworkRepository {

    suspend fun getSongsResult(): Result<List<Song>>
}