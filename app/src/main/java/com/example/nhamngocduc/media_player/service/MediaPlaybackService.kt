package com.example.nhamngocduc.media_player.service

import android.content.Intent
import android.net.Uri
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import com.example.nhamngocduc.domain.manager.SessionManager
import com.example.nhamngocduc.domain.repository.MusicRepository
import org.koin.android.ext.android.inject

class MediaPlaybackService : MediaSessionService() {

    private val musicRepository: MusicRepository by inject()
    private val sessionManager: SessionManager by inject()

    private var exoPlayer: ExoPlayer? = null
    private var mediaSession: MediaSession? = null

    override fun onCreate() {
        super.onCreate()
        exoPlayer = ExoPlayer.Builder(this).build()
        mediaSession = MediaSession.Builder(this, exoPlayer!!).build()
    }

    override fun onDestroy() {
        mediaSession?.run {
            player.release()
            release()
        }
        mediaSession = null
        exoPlayer = null
        super.onDestroy()
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession? {
        return mediaSession
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val songUriString = intent?.getStringExtra("SONG_URI")
        if (songUriString != null) {
            val mediaItem = MediaItem.fromUri(Uri.parse(songUriString))
            exoPlayer?.setMediaItem(mediaItem)
            exoPlayer?.prepare()
            exoPlayer?.playWhenReady = true
        }
        return super.onStartCommand(intent, flags, startId)
    }
}

