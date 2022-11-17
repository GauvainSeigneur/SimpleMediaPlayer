package com.rcudev.player_service.internal.data.controller

import android.util.Log
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.rcudev.player_service.internal.domain.repository.PlayerMediaRepository

internal class PlayerMediaController constructor(
    private val exoPlayer: ExoPlayer,
) : PlayerMediaRepository {


    override fun addItem(mediaItem: MediaItem) {
        Log.d("lolilol", "wesgh $mediaItem")
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()
    }

}
