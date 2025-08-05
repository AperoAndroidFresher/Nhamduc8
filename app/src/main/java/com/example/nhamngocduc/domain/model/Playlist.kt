package com.example.nhamngocduc.domain.model

import android.net.Uri

data class Playlist(
    val playlistId: Int = 0,
    val playlistName: String,
    val image: Uri? = null,
    val username: String
)
