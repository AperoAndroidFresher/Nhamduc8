package com.example.nhamngocduc.domain.usecases.playlist

import com.example.nhamngocduc.domain.repository.PlaylistRepository

class RemovePlaylist(
    private val playlistRepository: PlaylistRepository
) {
    suspend operator fun invoke(id: Int) {
        playlistRepository.deletePlaylist(id)
    }
}