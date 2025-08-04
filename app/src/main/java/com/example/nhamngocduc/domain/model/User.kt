package com.example.nhamngocduc.domain.model

import android.net.Uri

data class User(
    val username: String,
    val password: String,
    val email: String,
    val name: String?,
    val phone: String?,
    val university: String?,
    val description: String?,
    val profileImage: Uri?,
)