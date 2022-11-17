package com.rcudev.player_service.connector

import androidx.media3.common.MediaItem
import com.rcudev.player_service.internal.controller.PlayerControlEventController
import com.rcudev.player_service.internal.controller.PlayerServiceController
import com.rcudev.player_service.internal.di.InternalModule
import com.rcudev.player_service.internal.models.PlayerControlEvent

/**
 * Connector
 */
class PlayerConnector {

    private val playerServiceController : PlayerServiceController = InternalModule.getPlayerServiceController()
    private val playerControlEventController: PlayerControlEventController = InternalModule.getPlayerControlEventController()

    init {
        playerServiceController.startService()
        InternalModule.getPlayerEventListener() //todo : delete
    }

    /**
     * Stop all process (player, notification, etc.)
     */
    fun stopPlayerService() {
        playerServiceController.stopService()
    }

    fun playPause() {
        playerControlEventController.onControlEvent(PlayerControlEvent.PlayPause)
    }

    /**
     * Add item in player (it does not play it)
     */
    fun <T> addItem(t: T, transform: (T) -> MediaItem) {
        val addItemInPlayerUseCase = InternalModule.getPlayListController()
        addItemInPlayerUseCase.addItem(t, transform)
    }
}