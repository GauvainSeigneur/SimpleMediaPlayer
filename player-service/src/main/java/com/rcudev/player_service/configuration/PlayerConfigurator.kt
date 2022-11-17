package com.rcudev.player_service.configuration

import android.app.Application
import com.rcudev.player_service.configuration.module.LibraryModule

class PlayerConfigurator private constructor(
    application: Application,
) {

    init {
        LibraryModule.initializeDI(application)
    }

    class Builder {
        private lateinit var app: Application
        fun application(application: Application) = apply {
            app = application
        }
        fun build() = PlayerConfigurator(app)
    }
}