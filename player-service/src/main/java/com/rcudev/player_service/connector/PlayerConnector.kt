package com.rcudev.player_service.connector

import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.Timeline
import androidx.media3.exoplayer.ExoPlayer
import com.rcudev.player_service.configuration.module.LibraryModule
import com.rcudev.player_service.internal.controller.PlayerControlEventController
import com.rcudev.player_service.internal.controller.PlayerServiceController
import com.rcudev.player_service.internal.di.InternalModule
import com.rcudev.player_service.internal.models.PlayerControlEvent
import com.rcudev.player_service.internal.models.PlaylistItem

/**
 * Connector
 */
class PlayerConnector: Player.Listener {

    private val player: ExoPlayer = LibraryModule.player
    private val playerServiceController : PlayerServiceController = InternalModule.getPlayerServiceController()
    private val playerControlEventController: PlayerControlEventController = InternalModule.getPlayerControlEventController()
    private val playlistController = InternalModule.getPlayListController()

    // Listeners
    private var playlistChangeListener: PlaylistChangeListener? = null

    init {
        playerServiceController.startService()
        player.addListener(this)
    }

    // region service
    /**
     * Stop all process (player, notification, etc.)
     */
    fun stopPlayerService() {
        playerServiceController.stopService()
    }
    // endregion

    //region player controls
    fun playPause() {
        playerControlEventController.onControlEvent(PlayerControlEvent.PlayPause)
    }
    // endregion

    // region Playlist
    /**
     * Add item in player (it does not play it)
     */
    fun <T> addItem(t: T, transform: (T) -> MediaItem) {
        playlistController.addItem(t, transform)
    }

    /**
     * getPlaylist one shot
     */
    fun getPlaylist(): List<PlaylistItem> = playlistController.getPlaylist()

    /**
     * set listener to get a callback each time the playlist is updated
     */
    fun setPlayListChangeListener(listener: PlaylistChangeListener) {
        playlistChangeListener = listener
    }
    // endregion

    // region Player.Listener override
    override fun onTimelineChanged(timeline: Timeline, reason: Int) {
        super.onTimelineChanged(timeline, reason)
        playlistChangeListener?.onPlaylistChange(
            playlistController.getPlaylist()
        )
    }

    override fun onPlaybackStateChanged(playbackState: Int) {
        super.onPlaybackStateChanged(playbackState)
    }
    // endregion


}