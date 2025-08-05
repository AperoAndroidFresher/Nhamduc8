package com.example.nhamngocduc.data.local.room.dao

import android.net.Uri
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.nhamngocduc.data.local.model.entity.UserEntity
import com.example.nhamngocduc.data.local.model.entity.relation.UserWithPlaylists
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    
    @Query("SELECT * FROM users WHERE username = :username")
    fun getUser(username: String) : Flow<UserEntity?>

    @Query("SELECT username FROM users")
    fun getAllUsername() : Flow<List<String>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUser(user: UserEntity)

    @Query("UPDATE users " +
            "SET name = :name, phone = :phone, " +
            "university = :university, description = :description, " +
            "profile_image = :imageUri " +
            "WHERE username = :username")
    suspend fun updateAllProfileFieldsAtomically(
        username: String,
        name: String,
        phone: String,
        university: String,
        description: String,
        imageUri: Uri
    )
    @Transaction
    @Query("SELECT * FROM users WHERE username = :username")
    fun getUserWithPlaylists(username: String) : Flow<List<UserWithPlaylists>>
}