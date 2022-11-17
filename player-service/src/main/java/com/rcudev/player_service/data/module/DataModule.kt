package com.rcudev.player_service.data.module

import com.rcudev.player_service.configuration.module.LibraryModule
import com.rcudev.player_service.data.controller.MediaStateController
import com.rcudev.player_service.data.controller.PlayerControlEventController
import com.rcudev.player_service.data.controller.PlayerMediaController
import com.rcudev.player_service.data.controller.PlayerServiceController
import com.rcudev.player_service.data.handler.PlayerStateHandler
import com.rcudev.player_service.domain.repository.MediaStateRepository
import com.rcudev.player_service.domain.repository.PlayerControlEventRepository
import com.rcudev.player_service.domain.repository.PlayerMediaRepository
import com.rcudev.player_service.domain.repository.PlayerServiceRepository
import com.rcudev.player_service.domain.repository.PlayerStateRepository

object DataModule {

    // singleton
    private val playerServiceRepo: PlayerServiceRepository by lazy {
        PlayerServiceController(LibraryModule.application)
    }

    fun getMediaStateRepository(): MediaStateRepository = MediaStateController(LibraryModule.player)

    fun getPlayerControlEventRepository(): PlayerControlEventRepository = PlayerControlEventController(LibraryModule.player)

    fun getPlayerMediaRepository(): PlayerMediaRepository = PlayerMediaController(LibraryModule.player)

    fun getPlayerServiceRepository(): PlayerServiceRepository = playerServiceRepo

    fun getPlayerStateRepository(): PlayerStateRepository = PlayerStateHandler(
        LibraryModule.player,
        getMediaStateRepository()) //todo beurk!!!!
}