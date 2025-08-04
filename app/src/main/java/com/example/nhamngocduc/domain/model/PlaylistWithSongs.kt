package com.example.nhamngocduc.domain.model

data class PlaylistWithSongs(
    val playlist: Playlist,
    val songs: List<Song>
)