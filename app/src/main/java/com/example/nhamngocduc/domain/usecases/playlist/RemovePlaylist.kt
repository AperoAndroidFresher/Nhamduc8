package com.example.nhamngocduc.domain.usecases.playlist

import com.example.nhamngocduc.domain.repository.PlaylistRepository

class RemovePlaylist(
    private val repository: PlaylistRepository
) {
    suspend operator fun invoke(id: Int) {
        repository.removePlaylist(id)
    }
}