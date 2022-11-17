package com.rcudev.player_service.internal.listener

import android.util.Log
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer

/**
 * Global PlayerEvent listener:
 * Events
 * Commands
 */
internal class PlayerEventListener(private val player: ExoPlayer) : Player.Listener {

    init {
        player.addListener(this)
    }

    override fun onEvents(player: Player, events: Player.Events) {
        super.onEvents(player, events)
        Log.d("lolilol", "player $player, events ${events.get(0)}")
    }

    override fun onAvailableCommandsChanged(availableCommands: Player.Commands) {
        super.onAvailableCommandsChanged(availableCommands)
        Log.d("lolilol", "player $player, availableCommands ${availableCommands.get(0)}")
    }

}