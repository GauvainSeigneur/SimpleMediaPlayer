package com.rcudev.player_service.domain.repository

import com.rcudev.player_service.domain.models.RfPlayerControlEvent

interface PlayerControlEventRepository {

    suspend fun onControlEvent(event: RfPlayerControlEvent)
}