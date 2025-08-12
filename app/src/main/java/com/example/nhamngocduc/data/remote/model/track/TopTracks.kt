package com.example.nhamngocduc.data.remote.model.track

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class TopTracks(
    @SerializedName("track")
    val track: List<Track>
)