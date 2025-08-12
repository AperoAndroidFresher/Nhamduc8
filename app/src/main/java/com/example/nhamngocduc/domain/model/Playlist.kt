package com.example.nhamngocduc.domain.model

import android.net.Uri
import com.example.nhamngocduc.domain.model.serializer.UriSerializer
import kotlinx.serialization.Serializable

@Serializable
data class Playlist(
    val playlistId: Int = 0,
    val playlistName: String,
    val image:  @Serializable(with = UriSerializer::class) Uri? = null,
    val username: String
)
