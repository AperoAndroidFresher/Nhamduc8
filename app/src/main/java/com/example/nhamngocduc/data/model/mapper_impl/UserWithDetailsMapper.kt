package com.example.nhamngocduc.data.model.mapper_impl

import com.example.nhamngocduc.data.model.entity.relation.UserWithPlaylists
import com.example.nhamngocduc.domain.model.UserWithDetails
import com.example.nhamngocduc.util.EntityMapper

class UserWithDetailsMapper(
    private val userMapper: UserMapper,
    private val playlistMapper: PlaylistMapper
) : EntityMapper<UserWithDetails, UserWithPlaylists> {
    override fun mapFromEntity(entity: UserWithPlaylists): UserWithDetails =
        UserWithDetails(
            userMapper.mapFromEntity(entity.user),
            entity.playlists.map { playlistMapper.mapFromEntity(it) }
        )

    override fun mapToEntity(domain: UserWithDetails): UserWithPlaylists {
        TODO("Reading only so no need")
    }
}