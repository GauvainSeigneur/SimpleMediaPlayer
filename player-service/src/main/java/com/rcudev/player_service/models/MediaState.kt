package com.rcudev.player_service.models

/**
 * todo : rework
 */
sealed class MediaState {
    object Initial : MediaState()
    data class Ready(val duration: Long) : MediaState()
    data class Progress(val progress: Long) : MediaState()
    data class Buffering(val progress: Long) : MediaState()
    data class Playing(val isPlaying: Boolean) : MediaState()
}
