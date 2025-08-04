package com.example.nhamngocduc.data.repository

import android.net.Uri
import com.example.nhamngocduc.data.local.room.dao.UserDao
import com.example.nhamngocduc.data.model.entity.UserEntity
import com.example.nhamngocduc.data.model.entity.relation.UserWithPlaylists
import com.example.nhamngocduc.data.model.mapper_impl.UserMapper
import com.example.nhamngocduc.data.model.mapper_impl.UserWithDetailsMapper
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

    override suspend fun updateName(username: String, name: String) =
        userDao.updateName(name, username)

    override suspend fun updatePhone(username: String, phone: String) =
        userDao.updatePhone(phone, username)

    override suspend fun updateUniversity(username: String, university: String) =
        userDao.updateUniversity(university, username)

    override suspend fun updateDescription(username: String, description: String) =
        userDao.updateDescription(description, username)

    override suspend fun updateProfileImage(username: String, uri: Uri) =
        userDao.updateProfileImage(uri, username)

    override fun getUserWithDetails(username: String): Flow<UserWithDetails> =
        userDao.getUserWithPlaylists(username).map { listOfUserWithPlaylists ->
            listOfUserWithPlaylists.first().let { userWithPlaylist ->
                userWithDetailsMapper.mapFromEntity(userWithPlaylist)
            }
        }
}