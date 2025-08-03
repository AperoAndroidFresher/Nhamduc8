package com.example.nhamngocduc.domain.usecases.playlist

data class PlaylistUseCases(
    val getAllPlaylists: GetAllPlaylists,
    val getPlaylist: GetPlaylist,
    val removePlaylist: RemovePlaylist,
    val addPlaylist: AddNewPlaylist,
    val renamePlaylist: RenamePlaylist,
    val addSongToPlaylist: AddSongToPlaylist,
    val removeSongFromPlaylist: RemoveSongFromPlaylist
)