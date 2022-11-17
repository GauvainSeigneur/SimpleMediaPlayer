package com.rcudev.player_service.internal.data.controller

import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.rcudev.player_service.internal.domain.repository.PlayerMediaRepository

internal class PlayerMediaController constructor(
    private val exoPlayer: ExoPlayer,
) : PlayerMediaRepository {

    override fun addMediaItem(mediaItem: MediaItem) {
        exoPlayer.addMediaItem(mediaItem)
    }

    override fun setMediaItem(mediaItem: MediaItem) {
        exoPlayer.setMediaItem(mediaItem)
    }

}
