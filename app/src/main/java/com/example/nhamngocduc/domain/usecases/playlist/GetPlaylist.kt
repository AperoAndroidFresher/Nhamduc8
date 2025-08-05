package com.example.nhamngocduc.domain.usecases.playlist

import com.example.nhamngocduc.domain.model.Playlist
import com.example.nhamngocduc.domain.repository.PlaylistRepository
import kotlinx.coroutines.flow.Flow

class GetPlaylist(
    private val playlistRepository: PlaylistRepository
) {
    operator fun invoke(id: Int): Flow<Playlist?> {
        return playlistRepository.getPlaylist(id)
    }
}