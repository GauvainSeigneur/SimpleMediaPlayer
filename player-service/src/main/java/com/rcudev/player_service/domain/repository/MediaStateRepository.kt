package com.rcudev.player_service.domain.repository

import androidx.media3.common.Player
import com.rcudev.player_service.domain.models.RfMediaState
import kotlinx.coroutines.flow.StateFlow

interface MediaStateRepository {

    fun updateFromPlayback(@Player.State playbackState : Int)

    fun getMediaState(): StateFlow<RfMediaState>

    suspend fun startProgressUpdate()

    fun stopProgressUpdate()

    fun onIsPlayingChanged(isPlaying: Boolean)

}