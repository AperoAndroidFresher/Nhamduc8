package com.example.nhamngocduc.data.remote.model.album


import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class AlbumImage(
    @SerializedName("size")
    val size: String,
    @SerializedName("#text")
    val text: String?
)