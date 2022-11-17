package com.rcudev.player_service.internal.di

import com.rcudev.player_service.configuration.module.LibraryModule
import com.rcudev.player_service.internal.controller.PlayerControlEventController
import com.rcudev.player_service.internal.controller.PlayerServiceController
import com.rcudev.player_service.internal.controller.PlaylistController
import com.rcudev.player_service.internal.listener.PlayerEventListener

internal object InternalModule {

    private val playerEventListenerSingleton: PlayerEventListener by lazy {
        PlayerEventListener(LibraryModule.player)
    }

    private val playerServiceControllerSingleton: PlayerServiceController by lazy {
        PlayerServiceController(LibraryModule.application)
    }

    fun getPlayListController(): PlaylistController = PlaylistController(LibraryModule.player)

    fun getPlayerEventListener() : PlayerEventListener = playerEventListenerSingleton

    fun getPlayerServiceController() : PlayerServiceController = playerServiceControllerSingleton

    fun getPlayerControlEventController() : PlayerControlEventController = PlayerControlEventController(LibraryModule.player)

}