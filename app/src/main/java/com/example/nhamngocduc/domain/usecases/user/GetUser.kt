package com.example.nhamngocduc.domain.usecases.user

import com.example.nhamngocduc.domain.model.User
import com.example.nhamngocduc.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class GetUser(
    private val userRepository: UserRepository
) {
    operator fun invoke(username: String): Flow<User?> =
        userRepository.getUser(username)

}