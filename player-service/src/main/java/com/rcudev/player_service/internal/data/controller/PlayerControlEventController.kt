package com.rcudev.player_service.internal.data.controller

import androidx.media3.exoplayer.ExoPlayer
import com.rcudev.player_service.internal.domain.models.PlayerControlEvent
import com.rcudev.player_service.internal.domain.repository.PlayerControlEventRepository
import kotlinx.coroutines.*

/**
 * Class to handle events from control input (from notification or UI in application)
 * //todo remove it
 */
internal class PlayerControlEventController constructor(
    private val player: ExoPlayer,
) : PlayerControlEventRepository {

    private var job: Job? = null

    init {
        job = Job()
    }

    override suspend fun onControlEvent(event: PlayerControlEvent) {
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
            PlayerControlEvent.Stop -> {
                // do nothing
            }
            is PlayerControlEvent.UpdateProgress -> player.seekTo((player.duration * event.newProgress).toLong())
        }
    }

}
