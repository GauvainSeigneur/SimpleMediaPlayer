package com.rcudev.player_service.domain.usecase

import com.rcudev.player_service.domain.models.PlayerControlEvent
import com.rcudev.player_service.domain.repository.MediaStateRepository
import com.rcudev.player_service.domain.repository.PlayerControlEventRepository
import com.rcudev.player_service.domain.repository.PlayerStateRepository

class SetControlPlayerEventUseCase constructor(
    private val playerControlEventRepository: PlayerControlEventRepository,
    private val mediaStateRepository: MediaStateRepository,
    private val playerStateRepository: PlayerStateRepository,
) : suspend (PlayerControlEvent) -> Unit {

    override suspend fun invoke(controlEvent: PlayerControlEvent) {
        playerControlEventRepository.onControlEvent(controlEvent)
        when (controlEvent) {
            PlayerControlEvent.PlayPause -> {
                if (playerStateRepository.getIsPlaying()) {
                    mediaStateRepository.stopProgressUpdate()
                } else {
                    mediaStateRepository.onIsPlayingChanged(true)
                    mediaStateRepository.startProgressUpdate()
                }
            }
            PlayerControlEvent.Stop -> mediaStateRepository.stopProgressUpdate()
            else -> return
        }
    }
}