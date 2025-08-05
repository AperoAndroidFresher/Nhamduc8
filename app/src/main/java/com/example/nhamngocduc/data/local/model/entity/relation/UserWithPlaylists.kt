package com.example.nhamngocduc.data.local.model.entity.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.example.nhamngocduc.data.local.model.entity.PlaylistEntity
import com.example.nhamngocduc.data.local.model.entity.UserEntity

data class UserWithPlaylists(
    @Embedded val user: UserEntity,
    @Relation(
        parentColumn = "username",
        entityColumn = "username"
    )
    val playlists: List<PlaylistEntity>
)