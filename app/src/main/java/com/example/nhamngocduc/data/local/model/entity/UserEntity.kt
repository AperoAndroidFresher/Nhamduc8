package com.example.nhamngocduc.data.local.model.entity

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.nhamngocduc.data.local.room.coverter.UriTypeConverter

@Entity(tableName = "users")
data class UserEntity (
    @PrimaryKey(autoGenerate = false) val username: String,

    val password: String,
    val email: String,
    val name: String,
    val phone: String,
    val university: String,
    val description: String,

    @ColumnInfo(name = "profile_image")
    val profileImage: Uri?,
)