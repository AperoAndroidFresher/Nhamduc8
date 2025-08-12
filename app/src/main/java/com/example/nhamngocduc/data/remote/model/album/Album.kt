package com.example.nhamngocduc.data.remote.model.album

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Album(
    @SerializedName("artist")
    val albumArtist: AlbumArtist,
    @SerializedName("image")
    val albumImage: List<AlbumImage>?,
    @SerializedName("name")
    val name: String,
    @SerializedName("playcount")
    val playcount: Long,
    @SerializedName("url")
    val url: String,
)