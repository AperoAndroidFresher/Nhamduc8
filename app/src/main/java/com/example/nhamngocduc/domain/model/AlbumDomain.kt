package com.example.nhamngocduc.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class AlbumDomain(
    val artistName: String,
    val albumImageUrl: String?,
    val name: String,
    val url: String
)