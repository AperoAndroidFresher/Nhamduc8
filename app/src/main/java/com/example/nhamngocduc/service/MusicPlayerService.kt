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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MusicPlayerService : LifecycleService() {

    private var mediaPlayer: MediaPlayer? = null
    private var playlistId: Int = -1
    private var currentIndex: Int = -1

    companion object {
        val playbackStateFlow = MutableStateFlow(PlaybackState())
        val isRunningFlow = MutableStateFlow(false)

        const val CHANNEL_ID = "music_playback_channel"
        const val NOTIFICATION_ID = 1

        const val ACTION_PLAY_PAUSE = "ACTION_PLAY_PAUSE"
        const val ACTION_STOP = "ACTION_STOP"
        const val ACTION_NEXT = "ACTION_NEXT"
        const val ACTION_PREV = "ACTION_PREV"
        const val ACTION_PLAY_PLAYLIST = "ACTION_PLAY_PLAYLIST"
        const val EXTRA_PLAYLIST_ID = "EXTRA_PLAYLIST_ID"
        const val EXTRA_START_INDEX = "EXTRA_START_INDEX"
    }

    // Koin injection
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
            ACTION_PLAY_PLAYLIST -> {
                playlistId = intent.getIntExtra(EXTRA_PLAYLIST_ID, -1)
                currentIndex = intent.getIntExtra(EXTRA_START_INDEX, 0)
                if (playlistId != -1) playPlaylist(playlistId, currentIndex)
            }
        }
        return START_NOT_STICKY
    }

    private fun playPlaylist(id: Int, startIndex: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            playlistUseCases.getSongsFromPlaylist(id)
                .firstOrNull()?.let { playlistWithSongs ->
                    val songs = playlistWithSongs.songs
                    if (songs.isEmpty()) return@let
                    currentIndex = startIndex.coerceIn(songs.indices)
                    playCurrentSong(songs)
                }
        }
    }

    private fun playCurrentSong(songs: List<Song>) {
        if (songs.isEmpty() || currentIndex !in songs.indices) return
        val song = songs[currentIndex]

        mediaPlayer?.release()
        mediaPlayer = MediaPlayer().apply {
            setDataSource(applicationContext, song.contentUri!!)
            prepare()
            start()
            setOnCompletionListener { playNextSong(songs) }
        }
        emitState(song, songs)
        startForeground(NOTIFICATION_ID, buildNotification(song, true))
        startProgressUpdates(songs)
    }

    private fun playNextSong(songs: List<Song>? = null) {
        val currentPlaylist = songs ?: playbackStateFlow.value.playlist
        if (currentPlaylist.isEmpty()) return
        currentIndex = (currentIndex + 1) % currentPlaylist.size
        playCurrentSong(currentPlaylist)
    }

    private fun playPreviousSong() {
        val currentPlaylist = playbackStateFlow.value.playlist
        if (currentPlaylist.isEmpty()) return
        currentIndex = if (currentIndex - 1 < 0) currentPlaylist.size - 1 else currentIndex - 1
        playCurrentSong(currentPlaylist)
    }

    private fun togglePlayPause() {
        mediaPlayer?.let { if (it.isPlaying) it.pause() else it.start() }
        emitState()
    }

    private fun emitState(currentSong: Song? = null, playlist: List<Song>? = null) {
        val songs = playlist ?: playbackStateFlow.value.playlist
        val song = currentSong ?: songs.getOrNull(currentIndex)
        playbackStateFlow.value = PlaybackState(
            currentSong = song,
            isPlaying = mediaPlayer?.isPlaying ?: false,
            currentPosition = mediaPlayer?.currentPosition?.toLong() ?: 0L,
            totalDuration = mediaPlayer?.duration?.toLong() ?: 0L,
            playlist = songs,
            currentIndex = currentIndex
        )
        updateNotification()
    }

    private fun buildNotification(song: Song?, isPlaying: Boolean): Notification {
        val playPauseIntent = PendingIntent.getService(
            this, 0, Intent(this, MusicPlayerService::class.java).apply { action = ACTION_PLAY_PAUSE },
            PendingIntent.FLAG_IMMUTABLE
        )
        val nextIntent = PendingIntent.getService(
            this, 0, Intent(this, MusicPlayerService::class.java).apply { action = ACTION_NEXT },
            PendingIntent.FLAG_IMMUTABLE
        )
        val prevIntent = PendingIntent.getService(
            this, 0, Intent(this, MusicPlayerService::class.java).apply { action = ACTION_PREV },
            PendingIntent.FLAG_IMMUTABLE
        )
        val stopIntent = PendingIntent.getService(
            this, 0, Intent(this, MusicPlayerService::class.java).apply { action = ACTION_STOP },
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

    private fun updateNotification() {
        val song = playbackStateFlow.value.currentSong
        val isPlaying = playbackStateFlow.value.isPlaying
        startForeground(NOTIFICATION_ID, buildNotification(song, isPlaying))
    }

    private val handler = Handler(Looper.getMainLooper())
    private val progressRunnable = object : Runnable {
        override fun run() {
            emitState()
            handler.postDelayed(this, 500)
        }
    }
    private fun startProgressUpdates(songs: List<Song>) = handler.post(progressRunnable)
    private fun stopProgressUpdates() = handler.removeCallbacks(progressRunnable)

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, "Music Playback", NotificationManager.IMPORTANCE_LOW)
            getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onDestroy() {
        isRunningFlow.value = false
        mediaPlayer?.release()
        mediaPlayer = null
        stopProgressUpdates()
        super.onDestroy()
    }
}

