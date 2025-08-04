package com.example.nhamngocduc.domain.model

import android.net.Uri

data class Playlist(
    val playlistId: Int,
    val playlistName: String,
    val image: Uri?,
    val username: String
)
