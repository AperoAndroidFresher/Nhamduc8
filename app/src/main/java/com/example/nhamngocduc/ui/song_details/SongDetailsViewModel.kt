package com.example.nhamngocduc.ui.song_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import com.example.nhamngocduc.domain.usecases.music.SongUseCases
import com.example.nhamngocduc.domain.usecases.playlist.PlaylistUseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SongDetailsViewModel(
    private val mediaController: MediaController,
    private val songUseCases: SongUseCases,
    private val playlistUseCases: PlaylistUseCases
) : ViewModel() {
    private val _uiState = MutableStateFlow(SongDetailsContract.State())
    val uiState: StateFlow<SongDetailsContract.State> = _uiState.asStateFlow()

    private val playerListener = object : Player.Listener {
        override fun onIsPlayingChanged(isPlaying: Boolean) {
            _uiState.update { it.copy(isPlaying = isPlaying) }
        }

        override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
            // Update the state with the new playing song
            // You'll need to map the mediaItem.mediaId to your Song entity
        }

        override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {
            _uiState.update { it.copy(isShuffled = shuffleModeEnabled) }
        }

        override fun onRepeatModeChanged(repeatMode: Int) {
            val newRepeatMode = when (repeatMode) {
                Player.REPEAT_MODE_OFF -> RepeatMode.OFF
                Player.REPEAT_MODE_ONE -> RepeatMode.REPEAT_ONE
                Player.REPEAT_MODE_ALL -> RepeatMode.REPEAT_MULTIPLE
                else -> RepeatMode.OFF
            }
            _uiState.update { it.copy(repeatMode = newRepeatMode) }
        }

        override fun onPlaybackStateChanged(playbackState: Int) {
            if (playbackState == Player.STATE_ENDED && _uiState.value.playlistId == null) {
                // If a single song finishes, stop the service
                mediaController.setPlayWhenReady(false)
                // You'll need an intent to stop the service gracefully
            }
        }
    }

    fun processIntent(intent: SongDetailsContract.Intent) {
        when (intent) {
            is SongDetailsContract.Intent.LoadMedia -> {
                viewModelScope.launch {
                    val song = songUseCases.getSongById(intent.songId)
                    _uiState.update { it.copy(playingSong = song, playlistId = intent.playlistId) }

                    if (intent.playlistId != null) {
//                        val playlistWithSongs = playlistUseCases.getSongsFromPlaylist(intent.playlistId)
//                        val mediaItems = playlistWithSongs.first().map { MediaItem.fromUri(it.contentUri!!) }
//                        mediaController.setMediaItems(mediaItems)
//                        // Find the starting song in the playlist
//                        val startIndex = playlistWithSongs.first().indexOfFirst { it.songId == intent.songId }
//                        mediaController.seekTo(startIndex, 0)
//                        mediaController.prepare()
//                        mediaController.playWhenReady = true
                    } else {
                        // Play a single song
                        mediaController.setMediaItem(MediaItem.fromUri(song?.contentUri!!))
                        mediaController.prepare()
                        mediaController.playWhenReady = true
                    }
                }
            }
            is SongDetailsContract.Intent.PlayPause -> {
                if (_uiState.value.isPlaying) mediaController.pause() else mediaController.play()
            }
            is SongDetailsContract.Intent.SkipNext -> mediaController.seekToNext()
            is SongDetailsContract.Intent.SkipPrevious -> mediaController.seekToPrevious()
            is SongDetailsContract.Intent.ToggleShuffle -> {
                if (_uiState.value.playlistId != null) {
                    val isShuffled = !_uiState.value.isShuffled
                    mediaController.setShuffleModeEnabled(isShuffled)
                }
            }
            is SongDetailsContract.Intent.ToggleRepeatMode -> {
                if (_uiState.value.playlistId != null) {
                    val currentMode = _uiState.value.repeatMode
                    val newMode = when (currentMode) {
                        RepeatMode.OFF -> Player.REPEAT_MODE_ONE
                        RepeatMode.REPEAT_ONE -> Player.REPEAT_MODE_ALL
                        RepeatMode.REPEAT_MULTIPLE -> Player.REPEAT_MODE_OFF
                    }
                    mediaController.repeatMode = newMode
                }
            }
            is SongDetailsContract.Intent.SeekTo -> mediaController.seekTo(intent.position)
            is SongDetailsContract.Intent.StopService -> {
                // The service should have a way to stop itself via MediaController
            }
        }
    }

    init {
        mediaController.addListener(playerListener)
        // Set up other listeners for progress, duration, etc.
    }
}


