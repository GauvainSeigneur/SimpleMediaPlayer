package com.rcudev.player_service.internal.domain.repository

import com.rcudev.player_service.internal.domain.models.PlayerControlEvent

internal interface PlayerControlEventRepository {

    suspend fun onControlEvent(event: PlayerControlEvent)
}