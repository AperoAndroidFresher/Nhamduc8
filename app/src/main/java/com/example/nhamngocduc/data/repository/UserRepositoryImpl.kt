package com.example.nhamngocduc.data.repository

import android.net.Uri
import com.example.nhamngocduc.data.local.room.dao.UserDao
import com.example.nhamngocduc.data.local.model.mapper.UserMapper
import com.example.nhamngocduc.data.local.model.mapper.UserWithDetailsMapper
import com.example.nhamngocduc.domain.model.User
import com.example.nhamngocduc.domain.model.UserWithDetails
import com.example.nhamngocduc.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserRepositoryImpl(
    private val userDao: UserDao,
    private val userMapper: UserMapper,
    private val userWithDetailsMapper: UserWithDetailsMapper
) : UserRepository {

    override suspend fun insertUser(user: User) =
        userDao.insertUser(userMapper.mapToEntity(user))

    override fun getUser(username: String): Flow<User?> =
        userDao.getUser(username).map { userEntity ->
            userEntity?.let { userMapper.mapFromEntity(userEntity) }
        }

    override fun getAllUsername(): Flow<List<String>> =
        userDao.getAllUsername()

    override suspend fun updateProfileAtomically(
        username: String,
        name: String,
        phone: String,
        university: String,
        description: String,
        imageUri: Uri
    ) {
        userDao.updateAllProfileFieldsAtomically(username, name, phone, university, description, imageUri)
    }

    override fun getUserWithDetails(username: String): Flow<UserWithDetails?> =
        userDao.getUserWithPlaylists(username).map { listOfUserWithPlaylists ->
            listOfUserWithPlaylists.firstOrNull()?.let { userWithPlaylist ->
                userWithDetailsMapper.mapFromEntity(userWithPlaylist)
            }
        }
}