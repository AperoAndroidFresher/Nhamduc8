package com.example.nhamngocduc.domain.usecases.library

data class LibraryUseCases(
    val loadAllSongs: LoadAllSongs,
    val getSongPlaylistLink: GetSongPlaylistLink,
    val loadSongsFromRemote: LoadSongsFromRemote,
)