package com.rcudev.player_service.service

import android.content.Intent
import androidx.media3.common.Player
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import com.rcudev.player_service.configuration.module.LibraryModule

class SimpleMediaService : MediaSessionService() {

    private val mediaSession: MediaSession = LibraryModule.mediaSession

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaSession.run {
            release()
            // todo : why ?
            if (player.playbackState != Player.STATE_IDLE) {
                player.release()
            }
        }
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession =
        mediaSession
}