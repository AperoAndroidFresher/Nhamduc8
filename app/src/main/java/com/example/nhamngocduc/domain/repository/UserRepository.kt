package com.example.nhamngocduc.domain.repository

import android.net.Uri
import com.example.nhamngocduc.domain.model.User
import com.example.nhamngocduc.domain.model.UserWithDetails
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun insertUser(user: User)
    fun getUser(username: String): Flow<User?>
    fun getAllUsername(): Flow<List<String>>
    suspend fun updateProfileAtomically(
        username: String,
        name: String,
        phone: String,
        university: String,
        description: String,
        imageUri: Uri
    )
    fun getUserWithDetails(username: String): Flow<UserWithDetails?>
}