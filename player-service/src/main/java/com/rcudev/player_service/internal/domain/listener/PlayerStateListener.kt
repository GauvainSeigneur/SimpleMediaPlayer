package com.rcudev.player_service.internal.domain.listener

internal interface PlayerStateListener {

    fun onPlaybackStateChange()

    fun onError()

}