package com.example.nhamngocduc.domain.usecases.user

import android.net.Uri
import com.example.nhamngocduc.domain.repository.UserRepository

class UpdateProfileAtomically(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        username: String,
        name: String,
        phone: String,
        university: String,
        description: String,
        profileImg: Uri?
    ) = userRepository.updateProfileAtomically(
            username,
            name,
            phone,
            university,
            description, profileImg ?: Uri.EMPTY)

}