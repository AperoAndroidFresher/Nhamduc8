package com.example.nhamngocduc.data.remote.model.track

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Track(
    @SerializedName("artist")
    val trackArtist: TrackArtist,
    @SerializedName("image")
    val trackImage: List<TrackImage>,
    @SerializedName("listeners")
    val listeners: Long,
    @SerializedName("name")
    val trackName: String,
    @SerializedName("playcount")
    val playcount: Long,
    @SerializedName("url")
    val url: String
)