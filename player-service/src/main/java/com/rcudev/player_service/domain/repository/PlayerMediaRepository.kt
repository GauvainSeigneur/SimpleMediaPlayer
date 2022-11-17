package com.rcudev.player_service.domain.repository

import androidx.media3.common.MediaItem

interface PlayerMediaRepository {

    fun addItem(mediaItem: MediaItem)

}