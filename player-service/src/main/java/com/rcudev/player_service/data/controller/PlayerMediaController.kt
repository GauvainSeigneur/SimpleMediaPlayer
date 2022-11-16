package com.rcudev.player_service.data.controller

import androidx.media3.exoplayer.ExoPlayer
import com.rcudev.player_service.domain.models.RfMedia
import com.rcudev.player_service.domain.repository.PlayerMediaRepository
import com.rcudev.player_service.data.mapper.MediaItemMapper
import javax.inject.Inject

class PlayerMediaController @Inject constructor(
    private val exoPlayer: ExoPlayer,
    private val waitingListDataSource: WaitingListDataSource,
    private val mediaItemMapper: MediaItemMapper
) : PlayerMediaRepository {

    override fun addItem(rfMedia: RfMedia) {
        waitingListDataSource.addToWaitingList(rfMedia)
        exoPlayer.setMediaItem(mediaItemMapper.mapToMediaItem(rfMedia))
        exoPlayer.prepare()
    }

}
