package com.example.nhamngocduc.domain.usecases.playlist

import com.example.nhamngocduc.domain.repository.RelationRepository

class RemoveSongFromPlaylist(
    private val relationRepository: RelationRepository
) {
    suspend operator fun invoke(playlistId: Int, songId: Long) {
        relationRepository.deletePlaylistMusicCrossRef(playlistId, songId)
    }
}