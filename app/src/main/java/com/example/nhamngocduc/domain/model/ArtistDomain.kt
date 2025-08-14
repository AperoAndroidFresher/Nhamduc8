package com.example.nhamngocduc.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ArtistDomain(
    val artistImageUrl: String?,
    val name: String,
    val url: String
)