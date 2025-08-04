package com.example.nhamngocduc.data.model.entity.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.example.nhamngocduc.data.model.entity.PlaylistEntity
import com.example.nhamngocduc.data.model.entity.UserEntity

data class UserWithPlaylists(
    @Embedded val user: UserEntity,
    @Relation(
        parentColumn = "username",
        entityColumn = "username"
    )
    val playlists: List<PlaylistEntity>
)