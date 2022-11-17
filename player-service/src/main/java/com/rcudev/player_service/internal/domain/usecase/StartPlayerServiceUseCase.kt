package com.rcudev.player_service.internal.domain.usecase

import com.rcudev.player_service.internal.domain.repository.PlayerServiceRepository

internal class StartPlayerServiceUseCase constructor(private val playerServiceRepository: PlayerServiceRepository): () -> Boolean {

    override fun invoke(): Boolean = playerServiceRepository.startService()
}