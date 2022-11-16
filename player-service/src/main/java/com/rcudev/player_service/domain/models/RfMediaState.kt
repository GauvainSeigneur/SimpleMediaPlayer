package com.rcudev.player_service.domain.models

/**
 * todo : rework
 */
sealed class RfMediaState {
    object Initial : RfMediaState()
    data class Ready(val duration: Long) : RfMediaState()
    data class Progress(val progress: Long) : RfMediaState()
    data class Buffering(val progress: Long) : RfMediaState()
    data class Playing(val isPlaying: Boolean) : RfMediaState()
}
