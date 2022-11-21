package com.rcudev.player_service.internal.models

import androidx.media3.common.MediaItem

/**
 * Include mediaItem and another item relative to it
 */
data class PlaylistItem(
    val mediaItem: MediaItem,
    val item: Any,
)