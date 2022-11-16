package com.rcudev.player_service.handler

import androidx.media3.exoplayer.ExoPlayer
import com.rcudev.player_service.models.PlayerControlEvent
import kotlinx.coroutines.*
import javax.inject.Inject

/**
 * Class to handle events from control input (from notification or UI in application)
 */
class PlayerControlEventHandler @Inject constructor(
    private val player: ExoPlayer,
    private val mediaStateController: MediaStateController
)  {

    private var job: Job? = null

    init {
        job = Job()
    }

    suspend fun onPlayerEvent(playerControlEvent: PlayerControlEvent) {
        when (playerControlEvent) {
            PlayerControlEvent.Backward -> player.seekBack()
            PlayerControlEvent.Forward -> player.seekForward()
            PlayerControlEvent.PlayPause -> {
                if (player.isPlaying) {
                    player.pause()
                    mediaStateController.stopProgressUpdate()
                } else {
                    player.play()
                    mediaStateController.onIsPlayingChanged(true)
                    mediaStateController.startProgressUpdate()
                }
            }
            PlayerControlEvent.Stop -> mediaStateController.stopProgressUpdate()
            is PlayerControlEvent.UpdateProgress -> player.seekTo((player.duration * playerControlEvent.newProgress).toLong())
        }
    }

}
