package com.example.nhamngocduc.data.remote.model.artist

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class ArtistImage(
    @SerializedName("size")
    val size: String,
    @SerializedName("#text")
    val text: String?
)