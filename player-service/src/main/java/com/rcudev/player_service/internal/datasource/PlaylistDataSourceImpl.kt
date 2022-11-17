package com.rcudev.player_service.internal.datasource

import androidx.media3.common.MediaItem

class PlaylistDataSourceImpl: PlaylistDataSource {

    private val currentPlaylist : HashMap<Any, MediaItem> = HashMap()

    // todo : in a better world, we'll manipulate bdd or another memory allocation than a static list

    override fun setPlaylist() {
    }

    override fun updatePlaylist() {
    }

    override fun getPlaylist() {
    }
}