package com.example.nhamngocduc.domain.usecases.playlist

import com.example.nhamngocduc.domain.model.Playlist
import com.example.nhamngocduc.domain.repository.PlaylistRepository
import kotlinx.coroutines.flow.Flow

class GetAllPlaylists(
    private val playlistRepository: PlaylistRepository
) {
    operator fun invoke(username: String): Flow<List<Playlist>> {
        return playlistRepository.getAllPlaylists(username)
    }
}