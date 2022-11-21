package com.rcudev.player_service.internal.di

import com.rcudev.player_service.configuration.module.LibraryModule
import com.rcudev.player_service.internal.controller.PlayerControlEventController
import com.rcudev.player_service.internal.controller.PlayerServiceController
import com.rcudev.player_service.internal.controller.PlaylistController
import com.rcudev.player_service.internal.datasource.PlaylistDataSource

internal object InternalModule {

    // Singleton
    private val playerServiceControllerSingleton: PlayerServiceController by lazy {
        PlayerServiceController(LibraryModule.application)
    }

    private val playlistDataSourceSingleton: PlaylistDataSource by lazy {
        PlaylistDataSource(LibraryModule.player)
    }

    fun getPlayerServiceController(): PlayerServiceController = playerServiceControllerSingleton

    private fun getPlaylistDataSource(): PlaylistDataSource = playlistDataSourceSingleton

    fun getPlayListController(): PlaylistController = PlaylistController(LibraryModule.player, getPlaylistDataSource())

    fun getPlayerControlEventController(): PlayerControlEventController = PlayerControlEventController(LibraryModule.player)


}