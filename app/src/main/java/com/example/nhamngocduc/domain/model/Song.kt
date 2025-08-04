package com.example.nhamngocduc.domain.model

import android.net.Uri

data class Song(
    val songId: Long,
    val title: String,
    val artist: String,
    val duration: Long,
    val contentUri: Uri?
)