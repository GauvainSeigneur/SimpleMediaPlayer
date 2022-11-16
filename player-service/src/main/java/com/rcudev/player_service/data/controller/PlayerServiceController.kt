package com.rcudev.player_service.data.controller

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import com.rcudev.player_service.domain.repository.PlayerServiceRepository
import com.rcudev.player_service.service.SimpleMediaService
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class PlayerServiceController @Inject constructor(
    @ApplicationContext private val context: Context,
) : PlayerServiceRepository {

    private var isServiceRunning = false // todo : beurk

    override fun startService(): Boolean {
        return if (!isServiceRunning) {
            val intent = Intent(context, SimpleMediaService::class.java)
            ContextCompat.startForegroundService(context, intent)
            isServiceRunning = true
            true
        } else {
            false
        }
    }

    override fun stopService(): Boolean {
        context.stopService(Intent(context, SimpleMediaService::class.java))
        isServiceRunning = false
        return true
    }


}
