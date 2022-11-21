package com.rcudev.player_service.internal.controller

import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.rcudev.player_service.internal.datasource.PlaylistDataSource
import com.rcudev.player_service.internal.models.PlaylistItem

/**
 * Class to manipulates playlist
 */
internal class PlaylistController constructor(
    private val exoPlayer: ExoPlayer,
    private val playlistDataSource: PlaylistDataSource,
) {

    /**
     * Get playlist
     */
    fun getPlaylist(): List<PlaylistItem> {
        return playlistDataSource.getPlaylist()
    }

    /**
     * Add
     * add item o current playlist, create a new oen if null
     */

    fun <T> addItem(
        customMedia: T,
        transform: (T) -> MediaItem,
    ) {
        val mediaItem = transform(customMedia)
        playlistDataSource.addItemInPlaylist(mediaItem, customMedia)
        // finally add to exoPlayer
        exoPlayer.addMediaItem(mediaItem)
    }

    fun <T> addItem(customMedia: T, transform: (T) -> MediaItem, index: Int) {
        val mediaItem = transform(customMedia)
        playlistDataSource.addItemInPlaylist(mediaItem, customMedia)
        exoPlayer.addMediaItem(index, transform(customMedia))
    }

    fun <T> addItems(customMedias: List<T>, transform: (List<T>) -> List<MediaItem>) {
        val mediaItems = transform(customMedias)
        playlistDataSource.addItemsInPlaylist(
            mediaItems.zip(customMedias).toMap()
        )
        exoPlayer.addMediaItems(transform(customMedias))
    }

    fun <T> addItems(customMedias: List<T>, transform: (List<T>) -> List<MediaItem>, index: Int) {
        val mediaItems = transform(customMedias)
        playlistDataSource.addItemsInPlaylist(
            mediaItems.zip(customMedias).toMap()
        )
        exoPlayer.addMediaItems(index, mediaItems)
    }

    /**
     * Move
     */
    fun moveMediaItems(fromIndex: Int, toIndex: Int, newIndex: Int) {
        exoPlayer.moveMediaItems(fromIndex, toIndex, newIndex)
    }

    fun removeMediaItem(index: Int) {
        exoPlayer.removeMediaItem(index)
    }

    fun removeMediaItems(fromIndex: Int, toIndex: Int) {
        exoPlayer.removeMediaItems(fromIndex, toIndex)
    }

    /**
     * SET
     * Set clear the current playlist and create a new one with the new item
     */
    fun <T> setItem(
        customMedia: T,
        transform: (T) -> MediaItem,
    ) {
        exoPlayer.setMediaItem(transform(customMedia))
    }

    fun <T> setItem(
        customMedia: T,
        transform: (T) -> MediaItem,
        startPositionMs: Long,
    ) {
        exoPlayer.setMediaItem(transform(customMedia), startPositionMs)
    }

    fun <T> setItem(
        customMedia: T,
        transform: (T) -> MediaItem,
        resetPosition: Boolean,
    ) {
        exoPlayer.setMediaItem(transform(customMedia), resetPosition)
    }

    fun <T> setItems(customMedias: List<T>, transform: (List<T>) -> List<MediaItem>) {
        exoPlayer.setMediaItems(transform(customMedias))
    }

    fun <T> setItems(customMedias: List<T>, transform: (List<T>) -> List<MediaItem>, startIndex: Int, startPositionMs: Long) {
        exoPlayer.setMediaItems(transform(customMedias), startIndex, startPositionMs)
    }
}