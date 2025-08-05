package com.example.nhamngocduc.domain.usecases.user

data class UserUseCases(
    val getAllUsername: GetAllUsername,
    val getUser: GetUser,
    val insertUser: InsertUser,
    val getUserPlaylists: GetUserPlaylists,
    val updateProfileAtomically: UpdateProfileAtomically,
)