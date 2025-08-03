package com.example.nhamngocduc.domain.model

import androidx.annotation.DrawableRes
import com.example.nhamngocduc.R

data class Playlist(
    val id: Int,
    val playlistName: String,
    @DrawableRes val image: Int = R.drawable.folk_song,
    val songsList: List<Song> = emptyList()
)
