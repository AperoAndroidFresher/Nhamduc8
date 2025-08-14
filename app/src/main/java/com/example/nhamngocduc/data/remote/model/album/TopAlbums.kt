package com.example.nhamngocduc.data.remote.model.album


import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TopAlbums(
    @SerializedName("album")
    val album: List<Album>
)