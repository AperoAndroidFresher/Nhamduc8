package com.example.nhamngocduc.domain.usecases.user

import com.example.nhamngocduc.domain.model.Playlist
import com.example.nhamngocduc.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetUserPlaylists(
    private val userRepository: UserRepository
) {
    operator fun invoke(userName: String): Flow<List<Playlist>> =
        userRepository.getUserWithDetails(userName).map {
            it?.playlists ?: emptyList()
        }
}