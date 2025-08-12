package com.example.nhamngocduc.data.remote.model.album


import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class TopAlbumDto(
    @SerializedName("topalbums")
    val topalbums: TopAlbums
)