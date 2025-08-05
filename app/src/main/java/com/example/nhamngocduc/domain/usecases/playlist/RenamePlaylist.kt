package com.example.nhamngocduc.domain.usecases.playlist

import com.example.nhamngocduc.domain.repository.PlaylistRepository

class RenamePlaylist(
    private val playlistRepository: PlaylistRepository
) {
    suspend operator fun invoke(playlistId: Int, newName: String) {
        playlistRepository.renamePlaylist(playlistId, newName)
    }
}