package com.rcudev.simplemediaplayer.presenter

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import com.rcudev.player_service.connector.PlayerConnector
import com.rcudev.player_service.connector.PlaylistChangeListener
import com.rcudev.player_service.internal.models.PlaylistItem
import com.rcudev.simplemediaplayer.domain.AppBusinessMedia
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class SimpleMediaViewModel : ViewModel() {

    private val playerConnector = PlayerConnector()

    val itemA = AppBusinessMedia(
        id = "idOne",
        albumTitle = "SoundHelix ONE",
        url = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3")
    val secodndBuisnessMedia = AppBusinessMedia(
        id = "idTwo",
        albumTitle = "SoundHelix TWO",
        url = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-2.mp3")

    init {
        viewModelScope.launch {
        }

        playerConnector.setPlayListChangeListener(object : PlaylistChangeListener {
            override fun onPlaylistChange(list: List<PlaylistItem>) {
                // do your work in UI or domain to update the list too
                Log.d("walala", "list $list")
            }
        })
    }

    fun loadTrackOne() {
        playerConnector.addItem(
            itemA
        ) { appBusinessMedia ->
            mapToMediaItem(appBusinessMedia)
        }
    }

    fun loadTrackTwo() {
        playerConnector.addItem(
            secodndBuisnessMedia
        ) { appBusinessMedia ->
            mapToMediaItem(appBusinessMedia)
        }
    }

    fun playPause() {
        viewModelScope.launch {
            playerConnector.playPause()
        }
    }

    override fun onCleared() {
        viewModelScope.launch {
            //setControlPlayerEventUseCase(PlayerControlEvent.Stop)
        }
        playerConnector.stopPlayerService()
    }

    private fun mapToMediaItem(appBusinessMedia: AppBusinessMedia): MediaItem = MediaItem.Builder()
        //.setMediaId(appBusinessMedia.id) // not necessary with playlistItem // todo define if we want it as necessary
        .setUri(appBusinessMedia.url)
        .setMediaMetadata(
            MediaMetadata.Builder()
                .setFolderType(MediaMetadata.FOLDER_TYPE_ALBUMS)
                .setArtworkUri(Uri.parse("https://i.pinimg.com/736x/4b/02/1f/4b021f002b90ab163ef41aaaaa17c7a4.jpg"))
                .setAlbumTitle(appBusinessMedia.albumTitle)
                .setDisplayTitle("Song 1")
                .build()
        )
        .build()
}