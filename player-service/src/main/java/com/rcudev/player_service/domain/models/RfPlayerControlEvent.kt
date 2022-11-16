package com.rcudev.player_service.domain.models


sealed class RfPlayerControlEvent {
    object PlayPause : RfPlayerControlEvent()
    object Backward : RfPlayerControlEvent()
    object Forward : RfPlayerControlEvent()
    object Stop : RfPlayerControlEvent()
    data class UpdateProgress(val newProgress: Float) : RfPlayerControlEvent()
}