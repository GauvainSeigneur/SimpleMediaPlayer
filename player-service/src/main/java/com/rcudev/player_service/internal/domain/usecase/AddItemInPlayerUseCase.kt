package com.rcudev.player_service.internal.domain.usecase

import androidx.media3.common.MediaItem
import com.rcudev.player_service.internal.domain.repository.PlayerMediaRepository

internal class AddItemInPlayerUseCase<T> constructor(private val playerMediaRepository: PlayerMediaRepository): (T, (T) -> MediaItem) -> Unit {

    override fun invoke(customMedia: T, transform: (T) -> MediaItem) {
        // todo a repo to save the customMedia in order to return it if app want it  (don't lose data by the transform)
        playerMediaRepository.addMediaItem(transform(customMedia))
    }
}