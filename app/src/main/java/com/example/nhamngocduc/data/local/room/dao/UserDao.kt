package com.example.nhamngocduc.data.local.room.dao

import android.net.Uri
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.nhamngocduc.data.model.entity.UserEntity
import com.example.nhamngocduc.data.model.entity.relation.UserWithPlaylists
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    
    @Query("SELECT * FROM users WHERE username = :username")
    fun getUser(username: String) : Flow<UserEntity?>

    @Query("SELECT username FROM users")
    fun getAllUsername() : Flow<List<String>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUser(user: UserEntity)

    @Query("UPDATE users SET name = :newName WHERE username = :username")
    suspend fun updateName(newName: String, username: String)

    @Query("UPDATE users SET phone = :phone WHERE username = :username")
    suspend fun updatePhone(phone: String, username: String)

    @Query("UPDATE users SET name = :university WHERE username = :username")
    suspend fun updateUniversity(university: String, username: String)

    @Query("UPDATE users SET name = :description WHERE username = :username")
    suspend fun updateDescription(description: String, username: String)

    @Query("UPDATE users SET profile_image = :imageUri WHERE username = :username")
    suspend fun updateProfileImage(imageUri: Uri, username: String)

    @Transaction
    @Query("SELECT * FROM users WHERE username = :username")
    fun getUserWithPlaylists(username: String) : Flow<List<UserWithPlaylists>>
}