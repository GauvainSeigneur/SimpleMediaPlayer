package com.rcudev.player_service.internal.data.handler

import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.rcudev.player_service.internal.domain.repository.MediaStateRepository
import com.rcudev.player_service.internal.domain.repository.PlayerStateRepository
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * todo : check with vinc'
 * Class to handle events from [Player.Listener] events and send it to other repositories if necessary
 */
internal class PlayerStateHandler constructor(
    private val player: ExoPlayer,
    private val mediaStateRepository: MediaStateRepository,
) : Player.Listener, PlayerStateRepository {

    private var job: Job? = null

    init {
        player.addListener(this)
        job = Job()
    }

    override fun getIsPlaying(): Boolean = player.isPlaying

    override fun onPlaybackStateChanged(playbackState: Int) {
        mediaStateRepository.updateFromPlayback(playbackState)
    }

    override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
        when (reason) {
            Player.MEDIA_ITEM_TRANSITION_REASON_REPEAT -> {
            }
            else -> mediaItem?.let { item ->

            }
        }

        super.onMediaItemTransition(mediaItem, reason)
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onIsPlayingChanged(isPlaying: Boolean) {
        mediaStateRepository.onIsPlayingChanged(isPlaying)
        if (isPlaying) {
            GlobalScope.launch(Dispatchers.Main) {
                mediaStateRepository.startProgressUpdate()
            }
        } else {
            mediaStateRepository.stopProgressUpdate()
        }
    }
}
