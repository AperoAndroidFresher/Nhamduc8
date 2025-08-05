package com.example.nhamngocduc.data.local.model.entity

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "musics")
data class MusicEntity(
    @PrimaryKey(autoGenerate = true) val musicId: Long = 0L,

    val title: String,
    val artist: String,
    val duration: Long,
    @ColumnInfo(name = "content_uri")
    val contentUri: Uri?
)
