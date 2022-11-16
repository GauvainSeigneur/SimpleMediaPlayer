package com.rcudev.player_service.domain.repository

interface PlayerServiceRepository {

    fun startService() : Boolean

    fun stopService() : Boolean

}