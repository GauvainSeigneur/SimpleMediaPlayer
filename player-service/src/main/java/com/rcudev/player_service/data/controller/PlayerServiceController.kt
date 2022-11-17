package com.rcudev.player_service.data.controller

import android.content.Context
import android.content.Intent
import com.rcudev.player_service.domain.repository.PlayerServiceRepository
import com.rcudev.player_service.service.SimpleMediaService

class PlayerServiceController constructor(
    private val context: Context,
) : PlayerServiceRepository {

    private var isServiceRunning = false

    override fun startService(): Boolean {
        return if (!isServiceRunning) {
            // we add an action (Intent.ACTION_MEDIA_BUTTON) to the intent as the service expect it to notify MediaNotificationManager and
            // create a notification and set the service in foreground
            context.startForegroundService(
                Intent(
                    Intent.ACTION_MEDIA_BUTTON,
                    null,
                    context,
                    SimpleMediaService::class.java
                )
            )
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
