package com.rcudev.player_service.domain.listener

interface PlayerStateListener {

    fun onPlaybackStateChange()

    fun onError()

}