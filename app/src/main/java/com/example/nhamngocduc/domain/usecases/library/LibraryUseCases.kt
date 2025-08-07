package com.example.nhamngocduc.domain.usecases.library

data class LibraryUseCases(
    val loadSongsFromLocal: LoadSongsFromLocal,
    val getSongPlaylistLink: GetSongPlaylistLink,
    val loadSongsFromRemote: LoadSongsFromRemote,
)