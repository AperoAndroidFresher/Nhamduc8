package com.example.nhamngocduc.domain.model

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Song(
    val songId: Long = 0L,
    val localStoreId: Long? = null, // identify by path
    val remoteSourceId: String? = null,
    val title: String,
    val artist: String,
    val duration: Long,
    val contentUri: Uri?
) : Parcelable