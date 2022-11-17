package com.rcudev.player_service.internal.controller

import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer

/**
 * Class to manipulates playlist
 */
internal class PlaylistController constructor(
    private val exoPlayer: ExoPlayer,
) {

    /**
     * Add
     * add item o current playlist, create a new oen if null
     */

    fun <T>addItem(customMedia: T, transform: (T) -> MediaItem) {
        exoPlayer.addMediaItem(transform(customMedia))
    }

    fun <T>addItem(customMedia: T, transform: (T) -> MediaItem, index: Int) {
        exoPlayer.addMediaItem(index, transform(customMedia))
    }

    fun <T>addItems(customMedias: List<T>, transform: (List<T>) -> List<MediaItem>) {
        exoPlayer.addMediaItems(transform(customMedias))
    }

    fun <T>addItems(customMedias: List<T>, transform: (List<T>) -> List<MediaItem>, index: Int) {
        exoPlayer.addMediaItems(index, transform(customMedias))
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
    fun <T>setItem(
        customMedia: T,
        transform: (T) -> MediaItem,
    ) {
        exoPlayer.setMediaItem(transform(customMedia))
    }

    fun <T>setItem(
        customMedia: T,
        transform: (T) -> MediaItem,
        startPositionMs: Long,
    ) {
        exoPlayer.setMediaItem(transform(customMedia),startPositionMs)
    }

    fun <T>setItem(
        customMedia: T,
        transform: (T) -> MediaItem,
        resetPosition: Boolean,
    ) {
        exoPlayer.setMediaItem(transform(customMedia), resetPosition)
    }

    fun <T>setItems(customMedias: List<T>, transform: (List<T>) -> List<MediaItem>) {
        exoPlayer.setMediaItems(transform(customMedias))
    }

    fun <T>setItems(customMedias: List<T>, transform: (List<T>) -> List<MediaItem>, startIndex: Int,  startPositionMs: Long) {
        exoPlayer.setMediaItems(transform(customMedias), startIndex, startPositionMs)
    }
}