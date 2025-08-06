package com.example.nhamngocduc.data.local.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.nhamngocduc.util.GenreName

@Entity(tableName = "genre")
data class GenreEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val genreName: GenreName
)