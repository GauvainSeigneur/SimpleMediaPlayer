package com.rcudev.player_service.domain.usecase

import com.rcudev.player_service.domain.repository.PlayerServiceRepository
import javax.inject.Inject

class StartPlayerServiceUseCase @Inject constructor(private val playerServiceRepository: PlayerServiceRepository): () -> Boolean {

    override fun invoke(): Boolean = playerServiceRepository.startService()
}