package com.rcudev.player_service.handler

import androidx.media3.exoplayer.ExoPlayer
import com.rcudev.player_service.models.MediaState
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

/**
 * Class to handle a shared [MediaState] flow transformed by event on player and control event (input from user)
 */
class MediaStateController @Inject constructor(
    private val player: ExoPlayer,
)  {

    companion object {
        private const val PROGRESS_UPDATE_DELAY : Long = 500
    }

    internal val mediaState = MutableStateFlow<MediaState>(MediaState.Initial)

    private var job: Job? = null

    init {
        job = Job()
    }

    fun updateFromPlayback(playbackState: Int) {
        when (playbackState) {
            ExoPlayer.STATE_BUFFERING -> mediaState.value =
                MediaState.Buffering(player.currentPosition)
            ExoPlayer.STATE_READY -> mediaState.value =
                MediaState.Ready(player.duration)
        }
    }

    fun onIsPlayingChanged(isPlaying: Boolean) {
        mediaState.value = MediaState.Playing(isPlaying = isPlaying)
    }


    suspend fun startProgressUpdate() = job.run {
        while (true) {
            delay(PROGRESS_UPDATE_DELAY)
            mediaState.value = MediaState.Progress(player.currentPosition)
        }
    }

    fun stopProgressUpdate() {
        job?.cancel()
        mediaState.value = MediaState.Playing(isPlaying = false)
    }
}
