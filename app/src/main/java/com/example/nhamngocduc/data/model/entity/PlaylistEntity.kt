package com.example.nhamngocduc.data.model.entity

import android.net.Uri
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.nhamngocduc.data.local.room.coverter.UriTypeConverter

@Entity(
    tableName = "playlists",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["username"],
            childColumns = ["username"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class PlaylistEntity(
    @PrimaryKey(autoGenerate = true) val playlistId: Int = 0,

    val playlistName: String,
    val image: Uri?,
    val username: String,
)