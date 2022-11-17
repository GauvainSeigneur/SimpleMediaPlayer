package com.rcudev.player_service.internal.data.module

import com.rcudev.player_service.internal.domain.usecase.AddItemInPlayerUseCase
import com.rcudev.player_service.internal.domain.usecase.GetMediaStateUseCase
import com.rcudev.player_service.internal.domain.usecase.SetControlPlayerEventUseCase
import com.rcudev.player_service.internal.domain.usecase.StartPlayerServiceUseCase
import com.rcudev.player_service.internal.domain.usecase.StopPlayerServiceUseCase

internal object DomainModule {

    fun getStartPlayerServiceUseCase(): StartPlayerServiceUseCase = StartPlayerServiceUseCase(DataModule.getPlayerServiceRepository())

    fun getStopPlayerServiceUseCase(): StopPlayerServiceUseCase = StopPlayerServiceUseCase(DataModule.getPlayerServiceRepository())

    fun <T>getAddItemInPlayerUseCase(): AddItemInPlayerUseCase<T> = AddItemInPlayerUseCase(DataModule.getPlayerMediaRepository())

    fun getGetMediaStateUseCase(): GetMediaStateUseCase = GetMediaStateUseCase(DataModule.getMediaStateRepository())

    fun getSetControlPlayerEventUseCase(): SetControlPlayerEventUseCase = SetControlPlayerEventUseCase(
        DataModule.getPlayerControlEventRepository(),
        DataModule.getMediaStateRepository(),
        DataModule.getPlayerStateRepository()
    )
}