package com.example.nhamngocduc.domain.usecases.user

import com.example.nhamngocduc.domain.model.User
import com.example.nhamngocduc.domain.repository.UserRepository

class InsertUser(
    private val repository: UserRepository
) {
    suspend operator fun invoke(user: User) {
        repository.insertUser(user)
    }
}