package com.example.nhamngocduc.ui.now_playing

import com.example.nhamngocduc.domain.model.Song

data class PlaybackState(
    val currentSong: Song? = null,
    val isPlaying: Boolean = false,
    val currentPosition: Long = 0L,
    val totalDuration: Long = 0L,
    val playlist: List<Song> = emptyList(),
    val currentIndex: Int = -1,
    val isShuffled: Boolean = false,
    val isRepeated: Boolean = false,
    val isShuffleFinished: Boolean = false
)


