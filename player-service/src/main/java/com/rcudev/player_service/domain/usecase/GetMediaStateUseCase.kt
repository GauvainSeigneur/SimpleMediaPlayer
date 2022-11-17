package com.rcudev.player_service.domain.usecase

import com.rcudev.player_service.domain.repository.MediaStateRepository
import com.rcudev.player_service.domain.models.RfMediaState
import kotlinx.coroutines.flow.StateFlow

class GetMediaStateUseCase constructor(
    private val mediaStateRepository: MediaStateRepository
    ) :() -> StateFlow<RfMediaState> {

    override fun invoke(): StateFlow<RfMediaState> = mediaStateRepository.getMediaState()
}