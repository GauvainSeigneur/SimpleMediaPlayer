package com.rcudev.player_service.internal.domain.repository

internal interface PlayerServiceRepository {

    fun startService() : Boolean

    fun stopService() : Boolean

}