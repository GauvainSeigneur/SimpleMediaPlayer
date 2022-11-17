package com.rcudev.player_service.domain.models


sealed class PlayerControlEvent {
    object PlayPause : PlayerControlEvent()
    object Backward : PlayerControlEvent()
    object Forward : PlayerControlEvent()
    object Stop : PlayerControlEvent()
    data class UpdateProgress(val newProgress: Float) : PlayerControlEvent()
}