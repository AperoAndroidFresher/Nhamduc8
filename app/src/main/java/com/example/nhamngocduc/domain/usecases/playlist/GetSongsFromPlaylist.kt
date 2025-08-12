package com.example.nhamngocduc.domain.usecases.playlist

import android.util.Log
import com.example.nhamngocduc.domain.model.PlaylistWithSongs
import com.example.nhamngocduc.domain.model.Song
import com.example.nhamngocduc.domain.repository.PlaylistRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetSongsFromPlaylist(
    private val playlistRepository: PlaylistRepository
) {
    operator fun invoke(playlistId: Int): Flow<PlaylistWithSongs?> =
        playlistRepository.getPlaylistWithSongs(playlistId)
}