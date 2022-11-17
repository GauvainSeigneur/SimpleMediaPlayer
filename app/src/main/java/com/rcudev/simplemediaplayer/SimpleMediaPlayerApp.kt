package com.rcudev.simplemediaplayer

import android.app.Application
import com.rcudev.player_service.configuration.PlayerConfigurator

class SimpleMediaPlayerApp: Application() {

    override fun onCreate() {
        super.onCreate()
        PlayerConfigurator.Builder()
            .application(this)
            .build()
    }
}