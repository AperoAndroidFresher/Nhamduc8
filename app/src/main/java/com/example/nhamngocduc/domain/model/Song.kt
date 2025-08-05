package com.example.nhamngocduc.domain.model

import android.net.Uri

data class Song(
    val songId: Long = 0L,
    val title: String,
    val artist: String,
    val duration: Long,
    val contentUri: Uri?
)