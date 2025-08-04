package com.example.nhamngocduc.domain.usecases.playlist

import com.example.nhamngocduc.domain.repository.PlaylistRepository

class RemoveSongFromPlaylist(
    private val playlistRepository: PlaylistRepository
) {
    suspend operator fun invoke(playlistId: Int, songId: Long) {
        playlistRepository.removeSongFromPlaylist(playlistId, songId)
    }
}