package com.rcudev.player_service.domain.models

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
