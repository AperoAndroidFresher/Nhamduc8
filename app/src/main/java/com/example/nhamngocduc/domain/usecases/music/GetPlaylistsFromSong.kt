package com.example.nhamngocduc.domain.usecases.music

import com.example.nhamngocduc.domain.model.Playlist
import com.example.nhamngocduc.domain.repository.MusicRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetPlaylistsFromSong(
    private val musicRepository: MusicRepository
) {
    operator fun invoke(songId: Long): Flow<List<Playlist>> =
        musicRepository.getSongWithPlaylists(songId).map {
            it?.playlists ?: emptyList()
        }
}