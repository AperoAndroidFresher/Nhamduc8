package com.example.nhamngocduc.data.model.entity

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.nhamngocduc.data.local.room.coverter.UriTypeConverter

@Entity(tableName = "musics")
data class MusicEntity(
    @PrimaryKey(autoGenerate = true) val musicId: Long = 0L,

    val title: String,
    val artist: String,
    val duration: Long,
    @ColumnInfo(name = "content_uri")
    val contentUri: Uri?
)
