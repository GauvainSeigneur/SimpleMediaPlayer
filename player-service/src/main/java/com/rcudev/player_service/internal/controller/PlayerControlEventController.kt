package com.rcudev.player_service.internal.controller


import androidx.media3.exoplayer.ExoPlayer
import com.rcudev.player_service.internal.models.PlayerControlEvent

/**
 * Handle event from user input
 */
internal class PlayerControlEventController constructor(
    private val player: ExoPlayer,
) {

    fun onControlEvent(event: PlayerControlEvent) {
        player.prepare()
        when (event) {
            PlayerControlEvent.Backward -> player.seekBack()
            PlayerControlEvent.Forward -> player.seekForward()
            PlayerControlEvent.PlayPause -> {
                if (player.isPlaying) {
                    player.pause()
                } else {
                    player.play()
                }
            }
            PlayerControlEvent.Stop -> player.stop()
            is PlayerControlEvent.UpdateProgress -> player.seekTo((player.duration * event.newProgress).toLong())
        }
    }

}