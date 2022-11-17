package com.rcudev.player_service.domain.repository

import com.rcudev.player_service.domain.models.PlayerControlEvent

interface PlayerControlEventRepository {

    suspend fun onControlEvent(event: PlayerControlEvent)
}