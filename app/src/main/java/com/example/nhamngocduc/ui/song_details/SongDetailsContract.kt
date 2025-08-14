package com.example.nhamngocduc.ui.song_details

import com.example.nhamngocduc.domain.model.Playlist
import com.example.nhamngocduc.domain.model.Song

object SongDetailsContract  {
    data class State(
        val playlistId: Int? = null,
        val playingSong: Song? = null,
        val isPlaying: Boolean = false,
        val totalDuration: Long = 0L,
        val currentDuration: Long = 0L,
        val songProgress: Float = 0f,
        val isShuffled: Boolean = false,
        val repeatMode: RepeatMode = RepeatMode.OFF,
        val isLoading: Boolean = true
    )

    sealed interface Intent {
        data class LoadMedia(val playlistId: Int?, val songId: Long) : Intent
        object PlayPause : Intent
        object SkipNext : Intent
        object SkipPrevious : Intent
        object ToggleShuffle : Intent
        data object ToggleRepeatMode: Intent
        data class SeekTo(val position: Long) : Intent
        object StopService : Intent
    }
}

enum class RepeatMode {
    OFF,
    REPEAT_ONE,
    REPEAT_MULTIPLE
}