package com.rcudev.player_service.connector

import androidx.media3.common.MediaItem
import com.rcudev.player_service.data.module.DomainModule
import com.rcudev.player_service.domain.usecase.GetMediaStateUseCase
import com.rcudev.player_service.domain.usecase.SetControlPlayerEventUseCase
import com.rcudev.player_service.domain.usecase.StartPlayerServiceUseCase
import com.rcudev.player_service.domain.usecase.StopPlayerServiceUseCase

/**
 *
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

    /**
     * Add item in player (it does not play it)
     */
    fun <T>addItem(t:T, transform: (T) -> MediaItem) {
        DomainModule.getAddItemInPlayerUseCase<T>().invoke(t, transform)
    }

}