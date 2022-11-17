package com.rcudev.player_service.domain.usecase

import com.rcudev.player_service.domain.repository.MediaStateRepository
import com.rcudev.player_service.domain.models.PlayerMediaState
import kotlinx.coroutines.flow.StateFlow

class GetMediaStateUseCase constructor(
    private val mediaStateRepository: MediaStateRepository
    ) :() -> StateFlow<PlayerMediaState> {

    override fun invoke(): StateFlow<PlayerMediaState> = mediaStateRepository.getMediaState()
}