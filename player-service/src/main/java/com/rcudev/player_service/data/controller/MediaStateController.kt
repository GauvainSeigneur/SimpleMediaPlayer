package com.rcudev.player_service.data.controller

import androidx.media3.exoplayer.ExoPlayer
import com.rcudev.player_service.domain.repository.MediaStateRepository
import com.rcudev.player_service.domain.models.RfMediaState
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Class to handle a shared [RfMediaState] flow transformed by event on player and control event (input from user)
 */
class MediaStateController constructor(
    private val player: ExoPlayer,
): MediaStateRepository {

    companion object {
        private const val PROGRESS_UPDATE_DELAY : Long = 500
    }

    private val mediaState = MutableStateFlow<RfMediaState>(RfMediaState.Initial)

    private var job: Job? = null

    init {
        job = Job()
    }

    override fun getMediaState(): StateFlow<RfMediaState> = mediaState.asStateFlow()

    override fun updateFromPlayback(playbackState: Int) {
        when (playbackState) {
            ExoPlayer.STATE_BUFFERING -> mediaState.value =
                RfMediaState.Buffering(player.currentPosition)
            ExoPlayer.STATE_READY -> mediaState.value =
                RfMediaState.Ready(player.duration)
        }
    }

    override fun onIsPlayingChanged(isPlaying: Boolean) {
        mediaState.value = RfMediaState.Playing(isPlaying = isPlaying)
    }


    override suspend fun startProgressUpdate() = job.run {
        while (true) {
            delay(PROGRESS_UPDATE_DELAY)
            mediaState.value = RfMediaState.Progress(player.currentPosition)
        }
    }

    override fun stopProgressUpdate() {
        job?.cancel()
        mediaState.value = RfMediaState.Playing(isPlaying = false)
    }
}
