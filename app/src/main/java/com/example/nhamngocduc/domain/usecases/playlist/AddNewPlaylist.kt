package com.example.nhamngocduc.domain.usecases.playlist

import com.example.nhamngocduc.domain.repository.PlaylistRepository

class AddNewPlaylist(
    private val playlistRepository: PlaylistRepository
) {
    operator fun invoke(name: String) {
        playlistRepository.addNewPlaylist(name)
    }
}