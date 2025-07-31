package com.example.nhamngocduc.domain.model

import android.graphics.Bitmap
import android.net.Uri

data class Song(
    val id: Long,
    val title: String,
    val artist: String,
    val duration: Long,
    val contentUri: Uri,
    val data: String,
)