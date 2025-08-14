package com.example.nhamngocduc.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.Handler
import android.os.Looper
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import com.example.nhamngocduc.R
import com.example.nhamngocduc.domain.model.Song
import com.example.nhamngocduc.domain.usecases.playlist.PlaylistUseCases
import com.example.nhamngocduc.ui.now_playing.PlaybackState
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.firstOrNull
import org.koin.android.ext.android.inject

class MusicPlayerService : LifecycleService() {

    private var mediaPlayer: MediaPlayer? = null
    private var currentPlaylistId: Int? = null
    private var currentSongs: List<Song> = emptyList()
    private var currentIndex: Int = -1
    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private var pendingPlayJob: Job? = null

    // Shuffle logic
    private var shuffledOrder: MutableList<Int> = mutableListOf()
    private var shuffleIndex: Int = 0

    companion object {
        val playbackStateFlow = MutableStateFlow(PlaybackState())
        val isRunningFlow = MutableStateFlow(false)

        const val CHANNEL_ID = "music_playback_channel"
        const val NOTIFICATION_ID = 1

        const val ACTION_PLAY_PAUSE = "ACTION_PLAY_PAUSE"
        const val ACTION_STOP = "ACTION_STOP"
        const val ACTION_NEXT = "ACTION_NEXT"
        const val ACTION_PREV = "ACTION_PREV"
        const val ACTION_PLAY_SONG = "ACTION_PLAY_SONG"
        const val ACTION_TOGGLE_SHUFFLE = "ACTION_TOGGLE_SHUFFLE"
        const val ACTION_TOGGLE_REPEAT = "ACTION_TOGGLE_REPEAT"
        const val ACTION_SEEK = "ACTION_SEEK"

        const val EXTRA_PLAYLIST_ID = "EXTRA_PLAYLIST_ID"
        const val EXTRA_SONG_INDEX = "EXTRA_SONG_INDEX"
        const val EXTRA_SEEK_POSITION = "EXTRA_SEEK_POSITION"
    }

    private val playlistUseCases: PlaylistUseCases by inject()

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        isRunningFlow.value = true
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        when (intent?.action) {
            ACTION_PLAY_PAUSE -> togglePlayPause()
            ACTION_STOP -> stopSelf()
            ACTION_NEXT -> playNextSong()
            ACTION_PREV -> playPreviousSong()
            ACTION_PLAY_SONG -> {
                val playlistId = intent.getIntExtra(EXTRA_PLAYLIST_ID, -1)
                val index = intent.getIntExtra(EXTRA_SONG_INDEX, 0)
                if (playlistId != -1) playSongFromPlaylist(playlistId, index)
            }
            ACTION_TOGGLE_SHUFFLE -> toggleShuffle()
            ACTION_TOGGLE_REPEAT -> toggleRepeat()
            ACTION_SEEK -> {
                val position = intent.getLongExtra(EXTRA_SEEK_POSITION, 0L)
                seekTo(position)
            }
        }

        return START_NOT_STICKY
    }

    private fun playSongFromPlaylist(playlistId: Int, songIndex: Int) {
        pendingPlayJob?.cancel()

        pendingPlayJob = serviceScope.launch(Dispatchers.IO) {
            delay(300L)
            val playlistWithSongs = playlistUseCases.getSongsFromPlaylist(playlistId).firstOrNull()
            val songs = playlistWithSongs?.songs.orEmpty()
            if (songs.isEmpty()) return@launch

            currentPlaylistId = playlistId
            currentSongs = songs
            currentIndex = songIndex.coerceIn(songs.indices)

            if (playbackStateFlow.value.isShuffled) {
                buildShuffledOrder(preserveCurrentAsFirst = true)
                shuffleIndex = 0
            }

            withContext(Dispatchers.Main) {
                playCurrentSong()
            }
        }
    }

    private fun playCurrentSong() {
        if (currentSongs.isEmpty() || currentIndex !in currentSongs.indices) return
        val song = currentSongs[currentIndex]

        releaseMediaPlayerSafe()

        try {
            mediaPlayer = MediaPlayer().apply {
                setDataSource(applicationContext, song.contentUri!!)
                prepare()
                setOnCompletionListener { onSongCompleted() }
                start()
            }
        } catch (t: Throwable) {
            mediaPlayer?.release()
            mediaPlayer = null
        }

        emitState(song)
        startForeground(NOTIFICATION_ID, buildNotification(song, true))
        startProgressUpdates()
    }

    private fun prepareCurrentSong() {
        if (currentSongs.isEmpty() || currentIndex !in currentSongs.indices) return
        val song = currentSongs[currentIndex]

        releaseMediaPlayerSafe()

        try {
            mediaPlayer = MediaPlayer().apply {
                setDataSource(applicationContext, song.contentUri!!)
                prepare()
                setOnCompletionListener { onSongCompleted() }
            }
        } catch (t: Throwable) {
            mediaPlayer?.release()
            mediaPlayer = null
        }

        emitState(song)
        startForeground(NOTIFICATION_ID, buildNotification(song, false))
        stopProgressUpdates()
    }

    private fun onSongCompleted() {
        val state = playbackStateFlow.value

        if (state.isShuffled) {
            if (shuffleIndex < shuffledOrder.lastIndex) {
                shuffleIndex++
                currentIndex = shuffledOrder[shuffleIndex]
                playCurrentSong()
            } else {
                val firstIndex = if (shuffledOrder.isNotEmpty()) shuffledOrder[0] else 0
                currentIndex = firstIndex.coerceIn(currentSongs.indices)
                prepareCurrentSong()
                playbackStateFlow.value = playbackStateFlow.value.copy(
                    isShuffled = false,
                    isShuffleFinished = true,
                    isRepeated = false
                )
                emitState(currentSongs.getOrNull(currentIndex))
            }
        } else if (state.isRepeated) {
            currentIndex = if (currentIndex == currentSongs.lastIndex) 0 else currentIndex + 1
            playCurrentSong()
        } else {
            if (currentIndex == currentSongs.lastIndex) {
                currentIndex = 0
                prepareCurrentSong()
            } else {
                currentIndex++
                playCurrentSong()
            }
        }
    }

    private fun playNextSong() {
        if (currentSongs.isEmpty()) return
        val state = playbackStateFlow.value

        if (state.isShuffled) {
            if (shuffleIndex < shuffledOrder.lastIndex) {
                shuffleIndex++
            } else {
                shuffleIndex = 0
            }
            currentIndex = shuffledOrder[shuffleIndex]
        } else {
            currentIndex = if (currentIndex == currentSongs.lastIndex) 0 else currentIndex + 1
        }

        playCurrentSong()
    }

    private fun playPreviousSong() {
        if (currentSongs.isEmpty()) return
        val state = playbackStateFlow.value

        if (state.isShuffled) {
            if (shuffleIndex > 0) shuffleIndex-- else shuffleIndex = shuffledOrder.lastIndex
            currentIndex = shuffledOrder[shuffleIndex]
        } else {
            currentIndex = if (currentIndex - 1 < 0) currentSongs.lastIndex else currentIndex - 1
        }

        playCurrentSong()
    }

    private fun togglePlayPause() {
        try {
            mediaPlayer?.let {
                if (it.isPlaying) {
                    it.pause()
                    stopProgressUpdates()
                } else {
                    it.start()
                    startProgressUpdates()
                }
            }
        } catch (_: IllegalStateException) {

        }
        emitState()
    }

    private fun toggleShuffle() {
        val state = playbackStateFlow.value
        val newShuffleState = !state.isShuffled

        playbackStateFlow.value = state.copy(
            isShuffled = newShuffleState,
            isRepeated = if (newShuffleState) false else state.isRepeated,
            isShuffleFinished = if (newShuffleState) false else state.isShuffleFinished
        )

        if (newShuffleState) {
            buildShuffledOrder(preserveCurrentAsFirst = true)
            shuffleIndex = 0
        } else {
            shuffledOrder.clear()
            shuffleIndex = 0
            playbackStateFlow.value = playbackStateFlow.value.copy(isShuffleFinished = false)
        }
        emitState()
    }

    private fun toggleRepeat() {
        val state = playbackStateFlow.value
        val newRepeat = !state.isRepeated

        playbackStateFlow.value = state.copy(
            isRepeated = newRepeat,
            isShuffled = if (newRepeat) false else state.isShuffled,
            isShuffleFinished = if (newRepeat) false else state.isShuffleFinished
        )

        if (newRepeat) {
            shuffledOrder.clear()
            shuffleIndex = 0
        }
        emitState()
    }

    private fun seekTo(position: Long) {
        try {
            mediaPlayer?.seekTo(position.toInt())
        } catch (_: Throwable) { /* ignore */ }
        emitState()
    }

    private fun emitState(currentSong: Song? = null) {
        val song = currentSong ?: currentSongs.getOrNull(currentIndex)
        val isPlayingSafe = try { mediaPlayer?.isPlaying ?: false } catch (_: Throwable) { false }
        val currentPositionSafe = try { mediaPlayer?.currentPosition?.toLong() ?: 0L } catch (_: Throwable) { 0L }
        val totalDurationSafe = try { mediaPlayer?.duration?.toLong() ?: 0L } catch (_: Throwable) { 0L }

        playbackStateFlow.value = PlaybackState(
            currentSong = song,
            isPlaying = isPlayingSafe,
            currentPosition = currentPositionSafe,
            totalDuration = totalDurationSafe,
            playlist = currentSongs,
            currentIndex = currentIndex,
            isShuffled = playbackStateFlow.value.isShuffled,
            isRepeated = playbackStateFlow.value.isRepeated,
            isShuffleFinished = playbackStateFlow.value.isShuffleFinished
        )
        updateNotification()
    }

    private fun buildShuffledOrder(preserveCurrentAsFirst: Boolean) {
        val size = currentSongs.size
        if (size == 0) {
            shuffledOrder.clear()
            shuffleIndex = 0
            return
        }

        if (!preserveCurrentAsFirst) {
            shuffledOrder = (0 until size).shuffled().toMutableList()
            shuffleIndex = 0
            return
        }

        val cur = currentIndex.coerceIn(0, size - 1)
        val rest = (0 until size).filter { it != cur }.shuffled()
        shuffledOrder = mutableListOf(cur).apply { addAll(rest) }
        shuffleIndex = 0
    }

    private fun updateNotification() {
        val song = playbackStateFlow.value.currentSong
        val isPlaying = playbackStateFlow.value.isPlaying
        startForeground(NOTIFICATION_ID, buildNotification(song, isPlaying))
    }

    private fun buildNotification(song: Song?, isPlaying: Boolean): Notification {
        val playPauseIntent = PendingIntent.getService(
            this, 0, Intent(this, MusicPlayerService::class.java).apply { action = ACTION_PLAY_PAUSE },
            PendingIntent.FLAG_IMMUTABLE
        )
        val nextIntent = PendingIntent.getService(
            this, 1, Intent(this, MusicPlayerService::class.java).apply { action = ACTION_NEXT },
            PendingIntent.FLAG_IMMUTABLE
        )
        val prevIntent = PendingIntent.getService(
            this, 2, Intent(this, MusicPlayerService::class.java).apply { action = ACTION_PREV },
            PendingIntent.FLAG_IMMUTABLE
        )
        val stopIntent = PendingIntent.getService(
            this, 3, Intent(this, MusicPlayerService::class.java).apply { action = ACTION_STOP },
            PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(song?.title ?: "Unknown")
            .setContentText(song?.artist ?: "")
            .setSmallIcon(R.drawable.app_logo)
            .addAction(R.drawable.ic_skip_previous, "Prev", prevIntent)
            .addAction(if (isPlaying) R.drawable.ic_pause else R.drawable.ic_play, "Play/Pause", playPauseIntent)
            .addAction(R.drawable.ic_skip_next, "Next", nextIntent)
            .addAction(R.drawable.ic_close, "Stop", stopIntent)
            .setStyle(androidx.media.app.NotificationCompat.MediaStyle())
            .setOngoing(isPlaying)
            .build()
    }

    private val handler = Handler(Looper.getMainLooper())
    private val progressRunnable = object : Runnable {
        override fun run() {
            emitState()
            handler.postDelayed(this, 500)
        }
    }
    private fun startProgressUpdates() { handler.post(progressRunnable) }
    private fun stopProgressUpdates() { handler.removeCallbacks(progressRunnable) }

    private fun releaseMediaPlayerSafe() {
        try {
            mediaPlayer?.reset()
            mediaPlayer?.release()
        } catch (_: Throwable) {
        } finally {
            mediaPlayer = null
        }
        stopProgressUpdates()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, "Music Playback", NotificationManager.IMPORTANCE_LOW)
            getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onDestroy() {
        isRunningFlow.value = false
        releaseMediaPlayerSafe()
        serviceScope.cancel()
        stopForeground(STOP_FOREGROUND_REMOVE)
        super.onDestroy()
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        stopSelf()
    }
}