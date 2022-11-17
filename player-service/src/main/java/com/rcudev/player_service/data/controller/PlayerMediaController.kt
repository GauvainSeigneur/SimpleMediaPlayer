package com.rcudev.player_service.data.controller

import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.rcudev.player_service.domain.repository.PlayerMediaRepository

class PlayerMediaController constructor(
    private val exoPlayer: ExoPlayer,
) : PlayerMediaRepository {


    override fun addItem(mediaItem: MediaItem) {
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()
    }

}
