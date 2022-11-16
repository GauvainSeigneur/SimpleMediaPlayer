package com.rcudev.player_service.domain.usecase

import com.rcudev.player_service.domain.models.RfMedia
import com.rcudev.player_service.domain.models.RfMediaState
import com.rcudev.player_service.domain.repository.MediaStateRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetCurrentItemPlayedUseCase @Inject constructor() :() -> RfMedia {

    override fun invoke(): RfMedia {
        TODO("Not yet implemented")
    }


}