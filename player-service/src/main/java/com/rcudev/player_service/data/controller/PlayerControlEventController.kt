package com.rcudev.player_service.data.controller

import androidx.media3.exoplayer.ExoPlayer
import com.rcudev.player_service.domain.models.RfPlayerControlEvent
import com.rcudev.player_service.domain.repository.MediaStateRepository
import com.rcudev.player_service.domain.repository.PlayerControlEventRepository
import kotlinx.coroutines.*
import javax.inject.Inject

/**
 * Class to handle events from control input (from notification or UI in application)
 */
class PlayerControlEventController @Inject constructor(
    private val player: ExoPlayer,
) : PlayerControlEventRepository {

    private var job: Job? = null

    init {
        job = Job()
    }

    override suspend fun onControlEvent(event: RfPlayerControlEvent) {
        when (event) {
            RfPlayerControlEvent.Backward -> player.seekBack()
            RfPlayerControlEvent.Forward -> player.seekForward()
            RfPlayerControlEvent.PlayPause -> {
                if (player.isPlaying) {
                    player.pause()
                } else {
                    player.play()
                }
            }
            RfPlayerControlEvent.Stop -> {
                // do nothing
            }
            is RfPlayerControlEvent.UpdateProgress -> player.seekTo((player.duration * event.newProgress).toLong())
        }
    }

}
