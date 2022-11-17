package com.rcudev.player_service.internal.domain.usecase

import com.rcudev.player_service.internal.domain.models.PlayerControlEvent
import com.rcudev.player_service.internal.domain.repository.MediaStateRepository
import com.rcudev.player_service.internal.domain.repository.PlayerControlEventRepository
import com.rcudev.player_service.internal.domain.repository.PlayerStateRepository

internal class SetControlPlayerEventUseCase constructor(
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