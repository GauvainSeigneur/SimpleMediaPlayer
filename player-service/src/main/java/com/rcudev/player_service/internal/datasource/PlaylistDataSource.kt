package com.rcudev.player_service.internal.datasource

import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.rcudev.player_service.internal.models.PlaylistItem

/**
 * Class that acts like a DAO
 * Todo : to be rework with a real dataBase? (it currently works well as we inject it as singleton)
 */
class PlaylistDataSource(private val exoPlayer: ExoPlayer) {

    // todo : in a better world, we'll manipulate bdd or another memory allocation than a static list
    // so for this example, this class is provided as singleton
    private var currentPlaylist: MutableList<PlaylistItem> = mutableListOf()

    fun getPlaylist(): List<PlaylistItem> {
        // first get the current playlist from exoplayer
        updatePlaylistFromPlayer()
        // then returns corresponding objects from this
        return currentPlaylist
    }

    fun <T> addItemInPlaylist(mediaItem: MediaItem, item: T) {
        currentPlaylist.add(
            PlaylistItem(
                mediaItem,
                item as Any
            )
        )
    }

    fun <T> addItemsInPlaylist(map: Map<MediaItem, T>) {
        for (entry in map) {
            addItemInPlaylist(entry.key, entry.value)
        }
    }

    /**
     * We return a list and not a set because exoPlayer can return a list of the same MediaItem from the Playlist
     */
    private fun getPlayerPlaylistMedias(): List<MediaItem> {
        val medias = mutableListOf<MediaItem>()
        for (i in 0..exoPlayer.mediaItemCount) {
            medias.add(exoPlayer.getMediaItemAt(i))
        }
        return medias
    }

    private fun updatePlaylistFromPlayer() {
        val newPlaylist = mutableListOf<PlaylistItem>()
        getPlayerPlaylistMedias().map { mediaItem ->
            val item = currentPlaylist.firstOrNull { playlistItem -> playlistItem.mediaItem == mediaItem }
            item?.let { itemToRemove ->
                currentPlaylist.remove(itemToRemove)
                newPlaylist.add(item)
            }
        }
        currentPlaylist = newPlaylist
    }
}