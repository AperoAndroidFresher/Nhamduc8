package com.example.nhamngocduc.data.local.model.entity

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "musics",
    indices = [Index(value = ["remoteSourceId"], unique = true)]
)
data class MusicEntity(
    @PrimaryKey(autoGenerate = true) val musicId: Long = 0L,

    val localStoreId: Long? = null,
    val remoteSourceId: String? = null, // use path to identify each remote song
    val title: String,
    val artist: String,
    val duration: Long,
    @ColumnInfo(name = "file_path")
    val filePath: String?
)
