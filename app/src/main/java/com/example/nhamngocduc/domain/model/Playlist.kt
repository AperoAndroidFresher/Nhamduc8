package com.example.nhamngocduc.domain.model

import androidx.annotation.DrawableRes

data class Playlist(
    val id: Int,
    val playlistName: String,
    @DrawableRes val image: Int,
    val songsList: List<Song> = emptyList()
)
