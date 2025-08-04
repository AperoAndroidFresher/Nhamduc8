package com.example.nhamngocduc.domain.model

data class SongWithPlaylists(
    val song: Song,
    val playlists: List<Playlist>
)