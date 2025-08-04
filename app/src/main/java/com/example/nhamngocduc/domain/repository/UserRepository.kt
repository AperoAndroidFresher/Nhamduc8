package com.example.nhamngocduc.domain.repository

import android.net.Uri
import com.example.nhamngocduc.data.model.entity.relation.UserWithPlaylists
import com.example.nhamngocduc.domain.model.User
import com.example.nhamngocduc.domain.model.UserWithDetails
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun insertUser(user: User)
    fun getUser(username: String): Flow<User?>
    fun getAllUsername(): Flow<List<String>>
    suspend fun updateName(username: String, name: String)
    suspend fun updatePhone(username: String, phone: String)
    suspend fun updateUniversity(username: String, university: String)
    suspend fun updateDescription(username: String, description: String)
    suspend fun updateProfileImage(username: String, uri: Uri)
    fun getUserWithDetails(username: String): Flow<UserWithDetails>
}