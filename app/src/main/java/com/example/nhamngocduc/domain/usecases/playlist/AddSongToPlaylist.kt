package com.example.nhamngocduc.domain.usecases.playlist

import com.example.nhamngocduc.data.local.model.entity.relation.PlaylistMusicCrossRef
import com.example.nhamngocduc.domain.model.Playlist
import com.example.nhamngocduc.domain.model.Song
import com.example.nhamngocduc.domain.repository.RelationRepository

class AddSongToPlaylist(
    private val relationRepository: RelationRepository
) {

    suspend operator fun invoke(playlist: Playlist, song: Song) {
        val crossRef = PlaylistMusicCrossRef(
            playlist.playlistId,
            song.songId
        )

        relationRepository.insertPlaylistMusicCrossRef(crossRef)
    }
}