package com.example.nhamngocduc.data.model

import androidx.annotation.DrawableRes
import com.example.nhamngocduc.R

data class Song(
    val id: Int,
    @DrawableRes
    val songImage: Int = R.drawable.folk_song,
    val songName: String,
    val artistName: String,
    val songDuration: Long
)
