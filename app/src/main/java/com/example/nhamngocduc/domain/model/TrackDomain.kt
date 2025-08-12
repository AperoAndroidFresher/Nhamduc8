package com.example.nhamngocduc.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class TrackDomain(
    val artistName: String,
    val trackImageUrl: String?,
    val playcount: Long,
    val url: String,
    val name: String
)