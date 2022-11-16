package com.rcudev.player_service.domain.repository

interface PlayerStateRepository {

    fun getIsPlaying(): Boolean

}