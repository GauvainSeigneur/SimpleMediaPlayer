package com.rcudev.player_service.configuration.module

import android.app.Application
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector
import androidx.media3.session.MediaSession

object LibraryModule {

    @Volatile
    lateinit var application: Application

    private val audioAttributes: AudioAttributes by lazy {
        AudioAttributes.Builder()
            .setContentType(C.AUDIO_CONTENT_TYPE_MOVIE)
            .setUsage(C.USAGE_MEDIA)
            .build()
    }

    val player: ExoPlayer by lazy {
        ExoPlayer.Builder(application)
            .setAudioAttributes(audioAttributes, true)
            .setHandleAudioBecomingNoisy(true)
            .setTrackSelector(DefaultTrackSelector(application))
            .build()
    }

    val mediaSession: MediaSession by lazy {
        MediaSession.Builder(application, player).build()
    }

    fun initializeDI(app: Application) {
        if (!::application.isInitialized) {
            synchronized(this) {
                application = app
            }
        }
    }
}