package com.rcudev.player_service.internal.domain.usecase

import com.rcudev.player_service.internal.domain.repository.MediaStateRepository
import com.rcudev.player_service.internal.domain.models.PlayerMediaState
import kotlinx.coroutines.flow.StateFlow

internal class GetMediaStateUseCase constructor(
    private val mediaStateRepository: MediaStateRepository
    ) :() -> StateFlow<PlayerMediaState> {

    override fun invoke(): StateFlow<PlayerMediaState> = mediaStateRepository.getMediaState()
}