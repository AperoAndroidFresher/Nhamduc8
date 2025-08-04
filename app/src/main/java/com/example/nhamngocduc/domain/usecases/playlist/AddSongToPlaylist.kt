package com.example.nhamngocduc.domain.usecases.playlist

import com.example.nhamngocduc.domain.model.Song
import com.example.nhamngocduc.domain.repository.PlaylistRepository

class AddSongToPlaylist(
    private val playlistRepository: PlaylistRepository
) {
    suspend operator fun invoke(playlistId: Int, song: Song) {
        playlistRepository.addSongToPlaylist(playlistId, song)
    }
}