package com.rcudev.player_service.handler

import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 * Class to handle events from [Player.Listener] events
 */
class PlayerStateHandler @Inject constructor(
    player: ExoPlayer,
    private val mediaStateController: MediaStateController
) : Player.Listener {

    val simpleMediaState = mediaStateController.mediaState.asStateFlow()

    private var job: Job? = null

    init {
        player.addListener(this)
        job = Job()
    }

    override fun onPlaybackStateChanged(playbackState: Int) {
        mediaStateController.updateFromPlayback(playbackState)
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onIsPlayingChanged(isPlaying: Boolean) {
        mediaStateController.onIsPlayingChanged(isPlaying)
        if (isPlaying) {
            GlobalScope.launch(Dispatchers.Main) {
                mediaStateController.startProgressUpdate()
            }
        } else {
            mediaStateController.stopProgressUpdate()
        }
    }
}
