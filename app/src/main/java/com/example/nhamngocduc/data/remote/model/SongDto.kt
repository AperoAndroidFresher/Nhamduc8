package com.example.nhamngocduc.data.remote.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class SongDto(
    @SerializedName("artist")
    val artist: String,
    @SerializedName("duration")
    val duration: String,
    @SerializedName("kind")
    val kind: String,
    @SerializedName("path")
    val path: String,
    @SerializedName("title")
    val title: String
)