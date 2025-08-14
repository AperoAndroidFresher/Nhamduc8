package com.example.nhamngocduc.data.remote.model.artist

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Artist(
    @SerializedName("image")
    val artistImage: List<ArtistImage>,
    @SerializedName("listeners")
    val listeners: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("playcount")
    val playcount: Long,
    @SerializedName("url")
    val url: String
)