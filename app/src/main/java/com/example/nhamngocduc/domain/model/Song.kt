package com.example.nhamngocduc.domain.model

import android.net.Uri

data class Song(
    val songId: Long = 0L,
    val localStoreId: Long? = null,
    val remoteSourceId: Long? = null,
    val title: String,
    val artist: String,
    val genre: String = "",
    val duration: Long,
    val contentUri: Uri?
)