package com.example.nhamngocduc.data.model.mapper_impl

import com.example.nhamngocduc.data.model.entity.UserEntity
import com.example.nhamngocduc.domain.model.User
import com.example.nhamngocduc.util.EntityMapper

class UserMapper : EntityMapper<User, UserEntity> {
    override fun mapFromEntity(entity: UserEntity): User =
        User(
            entity.username,
            entity.password,
            entity.email,
            entity.name,
            entity.phone,
            entity.university,
            entity.description,
            entity.profileImage
        )

    override fun mapToEntity(domain: User): UserEntity =
        UserEntity(
            domain.username,
            domain.password,
            domain.email,
            domain.name,
            domain.phone,
            domain.university,
            domain.description,
            domain.profileImage
        )

}