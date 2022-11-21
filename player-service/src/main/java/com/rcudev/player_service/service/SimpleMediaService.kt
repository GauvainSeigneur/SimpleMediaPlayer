package com.rcudev.player_service.service

import androidx.media3.common.Player
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import com.rcudev.player_service.configuration.module.LibraryModule

class SimpleMediaService : MediaSessionService() {

    private val mediaSession: MediaSession = LibraryModule.mediaSession

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

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession = mediaSession
}