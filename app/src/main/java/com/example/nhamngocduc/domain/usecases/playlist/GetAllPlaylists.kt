package com.example.nhamngocduc.domain.usecases.playlist

import com.example.nhamngocduc.domain.model.Playlist
import com.example.nhamngocduc.domain.repository.PlaylistRepository
import kotlinx.coroutines.flow.Flow

class GetAllPlaylists(
    private val repository: PlaylistRepository
) {
    operator fun invoke(): Flow<List<Playlist>> {
        return repository.getAllPlaylists()
    }
}