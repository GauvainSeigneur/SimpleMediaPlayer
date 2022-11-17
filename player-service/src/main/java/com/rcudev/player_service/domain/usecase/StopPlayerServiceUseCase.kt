package com.rcudev.player_service.domain.usecase

import com.rcudev.player_service.domain.repository.PlayerServiceRepository

class StopPlayerServiceUseCase constructor(private val playerServiceRepository: PlayerServiceRepository): () -> Boolean {

    override fun invoke(): Boolean = playerServiceRepository.stopService()
}