package com.rcudev.player_service.connector

import com.rcudev.player_service.internal.models.PlaylistItem

interface PlaylistChangeListener {

    fun onPlaylistChange(list: List<PlaylistItem>)
}