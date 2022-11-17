package com.rcudev.player_service.connector

import androidx.media3.common.MediaItem
import com.rcudev.player_service.internal.data.module.DomainModule
import com.rcudev.player_service.internal.domain.models.PlayerControlEvent
import com.rcudev.player_service.internal.domain.usecase.GetMediaStateUseCase
import com.rcudev.player_service.internal.domain.usecase.SetControlPlayerEventUseCase
import com.rcudev.player_service.internal.domain.usecase.StartPlayerServiceUseCase
import com.rcudev.player_service.internal.domain.usecase.StopPlayerServiceUseCase

/**
 * Simple class
 */
class PlayerConnector {

    private val startPlayerServiceUseCase: StartPlayerServiceUseCase = DomainModule.getStartPlayerServiceUseCase()
    private val stopPlayerServiceUseCase: StopPlayerServiceUseCase = DomainModule.getStopPlayerServiceUseCase()
    private val getMediaStateUseCase: GetMediaStateUseCase = DomainModule.getGetMediaStateUseCase()
    private val setControlPlayerEventUseCase: SetControlPlayerEventUseCase = DomainModule.getSetControlPlayerEventUseCase()

    init {
        startPlayerServiceUseCase()
    }


    /**
     * Stop all process (player, notification, etc.)
     */
    fun stopPlayerService() {
        stopPlayerServiceUseCase()
    }

    suspend fun playPause() {
        setControlPlayerEventUseCase(PlayerControlEvent.PlayPause)
    }

    /**
     * Add item in player (it does not play it)
     */
    fun <T>addItem(t:T, transform: (T) -> MediaItem) {
        val addItemInPlayerUseCase = DomainModule.getAddItemInPlayerUseCase<T>()
        addItemInPlayerUseCase.invoke(t, transform)
    }

}