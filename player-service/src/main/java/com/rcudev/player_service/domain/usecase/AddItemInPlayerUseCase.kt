package com.rcudev.player_service.domain.usecase

import com.rcudev.player_service.domain.models.RfMedia
import com.rcudev.player_service.domain.repository.PlayerMediaRepository

class AddItemInPlayerUseCase constructor(private val playerMediaRepository: PlayerMediaRepository): (RfMedia) -> Unit {

    override fun invoke(rfMedia: RfMedia) {
        playerMediaRepository.addItem(rfMedia)
    }
}