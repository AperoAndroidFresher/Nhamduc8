package com.example.nhamngocduc.domain.usecases.playlist

import com.example.nhamngocduc.domain.model.Playlist
import com.example.nhamngocduc.domain.repository.PlaylistRepository

class AddNewPlaylist(
    private val playlistRepository: PlaylistRepository
) {
    suspend operator fun invoke(playlist: Playlist) {
        playlistRepository.addNewPlaylist(playlist)
    }
}