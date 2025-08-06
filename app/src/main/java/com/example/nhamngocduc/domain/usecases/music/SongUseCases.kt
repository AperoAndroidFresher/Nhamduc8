package com.example.nhamngocduc.domain.usecases.music

data class SongUseCases (
    val insertSong: InsertSong,
    val getAllSongs: GetAllSongs,
    val getPlaylistsFromSong: GetPlaylistsFromSong,
    val getSongByRemoteId: GetSongByRemoteId,
    val getSongByLocalId: GetSongByLocalId
)