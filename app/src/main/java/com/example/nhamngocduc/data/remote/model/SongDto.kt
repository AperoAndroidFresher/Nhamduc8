package com.example.nhamngocduc.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SongDto(
    @SerialName("artist")
    val artist: String,
    @SerialName("duration")
    val duration: String,
    @SerialName("kind")
    val kind: String,
    @SerialName("path")
    val path: String,
    @SerialName("title")
    val title: String
)