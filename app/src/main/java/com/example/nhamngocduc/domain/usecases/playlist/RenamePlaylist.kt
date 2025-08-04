package com.example.nhamngocduc.domain.usecases.playlist

import com.example.nhamngocduc.domain.repository.PlaylistRepository

class RenamePlaylist(
    private val playlistRepository: PlaylistRepository
) {
    suspend operator fun invoke(id: Int, newName: String) {
        playlistRepository.renamePlaylist(id, newName)
    }
}