package com.example.nhamngocduc.data.remote.model.artist

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class TopArtistDto(
    @SerializedName("artists")
    val topArtists: TopArtists
)