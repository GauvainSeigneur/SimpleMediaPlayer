package com.rcudev.player_service.internal.models

/**
 * todo : rework
 */
sealed class PlayerMediaState {
    object Initial : PlayerMediaState()
    data class Ready(val duration: Long) : PlayerMediaState()
    data class Progress(val progress: Long) : PlayerMediaState()
    data class Buffering(val progress: Long) : PlayerMediaState()
    data class Playing(val isPlaying: Boolean) : PlayerMediaState()
}
