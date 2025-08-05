package com.example.nhamngocduc.domain.usecases.user

import com.example.nhamngocduc.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class GetAllUsername(
    private val userRepository: UserRepository
) {
     operator fun invoke(): Flow<List<String>> =
        userRepository.getAllUsername()

}