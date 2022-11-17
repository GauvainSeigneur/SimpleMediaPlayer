package com.rcudev.player_service.internal.domain.repository

import androidx.media3.common.MediaItem

internal interface PlayerMediaRepository {

    fun addMediaItem(mediaItem: MediaItem)

    fun setMediaItem(mediaItem: MediaItem)
}