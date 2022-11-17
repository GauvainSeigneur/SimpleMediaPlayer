package com.rcudev.player_service.internal.controller

import android.app.Application
import android.content.Intent
import androidx.core.content.ContextCompat
import com.rcudev.player_service.service.SimpleMediaService

/**
 * Class to manipulates playlist
 */
internal class PlayerServiceController constructor(
    private val application: Application,
) {

    private var isServiceRunning = false

    fun startService(): Boolean {
        return if (!isServiceRunning) {
            // we add an action (Intent.ACTION_MEDIA_BUTTON) to the intent as the service expect it to notify MediaNotificationManager and
            // create a notification and set the service in foreground
            ContextCompat.startForegroundService(
                application,
                Intent(
                    Intent.ACTION_MEDIA_BUTTON,
                    null,
                    application,
                    SimpleMediaService::class.java
                )
            )
            isServiceRunning = true
            true
        } else {
            false
        }
    }

    fun stopService(): Boolean {
        application.stopService(Intent(application, SimpleMediaService::class.java))
        isServiceRunning = false
        return true
    }

}