package com.rcudev.player_service.data.module

import com.rcudev.player_service.domain.usecase.AddItemInPlayerUseCase
import com.rcudev.player_service.domain.usecase.GetMediaStateUseCase
import com.rcudev.player_service.domain.usecase.SetControlPlayerEventUseCase
import com.rcudev.player_service.domain.usecase.StartPlayerServiceUseCase
import com.rcudev.player_service.domain.usecase.StopPlayerServiceUseCase

object DomainModule {

    fun getStartPlayerServiceUseCase(): StartPlayerServiceUseCase = StartPlayerServiceUseCase(DataModule.getPlayerServiceRepository())

    fun getStopPlayerServiceUseCase(): StopPlayerServiceUseCase = StopPlayerServiceUseCase(DataModule.getPlayerServiceRepository())

    fun getAddItemInPlayerUseCase(): AddItemInPlayerUseCase = AddItemInPlayerUseCase(DataModule.getPlayerMediaRepository())

    fun getGetMediaStateUseCase(): GetMediaStateUseCase = GetMediaStateUseCase(DataModule.getMediaStateRepository())
    
    fun getSetControlPlayerEventUseCase(): SetControlPlayerEventUseCase = SetControlPlayerEventUseCase(
        DataModule.getPlayerControlEventRepository(),
        DataModule.getMediaStateRepository(),
        DataModule.getPlayerStateRepository()
    )
}