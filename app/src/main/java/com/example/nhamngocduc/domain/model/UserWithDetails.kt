package com.example.nhamngocduc.domain.model

data class UserWithDetails(
    val user: User,
    val playlists: List<Playlist>
)
