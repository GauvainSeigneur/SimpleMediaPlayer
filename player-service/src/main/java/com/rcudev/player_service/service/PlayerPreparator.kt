package com.rcudev.player_service.service

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import androidx.media3.exoplayer.ExoPlayer
import com.rcudev.player_service.domain.RfMedia
import com.rcudev.player_service.service.mapper.MediaItemMapper
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class PlayerPreparator @Inject constructor(
    private val player: ExoPlayer,
    private val mediaItemMapper: MediaItemMapper
)  {

    private var isServiceRunning = false // todo : beurk

    fun startPlayerService(context: Context) {
        if (!isServiceRunning) {
            val intent = Intent(context, SimpleMediaService::class.java)
            ContextCompat.startForegroundService(context, intent)
            isServiceRunning = true
        }
    }

    fun stopPlayerService(context: Context) {
        context.stopService(Intent(context, SimpleMediaService::class.java))
        isServiceRunning = false
    }

    fun addMediaItem(rfMedia: RfMedia) {
        player.setMediaItem(mediaItemMapper.mapToMediaItem(rfMedia))
        player.prepare()
    }

}
