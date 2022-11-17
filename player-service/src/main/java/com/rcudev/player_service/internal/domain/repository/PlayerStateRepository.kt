package com.rcudev.player_service.internal.domain.repository

internal interface PlayerStateRepository {

    fun getIsPlaying(): Boolean

}