package com.rcudev.player_service.domain.usecase

import com.rcudev.player_service.domain.models.RfPlayerControlEvent
import com.rcudev.player_service.domain.repository.MediaStateRepository
import com.rcudev.player_service.domain.repository.PlayerControlEventRepository
import com.rcudev.player_service.domain.repository.PlayerStateRepository

class SetControlPlayerEventUseCase constructor(
    private val playerControlEventRepository: PlayerControlEventRepository,
    private val mediaStateRepository: MediaStateRepository,
    private val playerStateRepository: PlayerStateRepository,
) : suspend (RfPlayerControlEvent) -> Unit {

    override suspend fun invoke(controlEvent: RfPlayerControlEvent) {
        playerControlEventRepository.onControlEvent(controlEvent)
        when (controlEvent) {
            RfPlayerControlEvent.PlayPause -> {
                if (playerStateRepository.getIsPlaying()) {
                    mediaStateRepository.stopProgressUpdate()
                } else {
                    mediaStateRepository.onIsPlayingChanged(true)
                    mediaStateRepository.startProgressUpdate()
                }
            }
            RfPlayerControlEvent.Stop -> mediaStateRepository.stopProgressUpdate()
            else -> return
        }
    }
}