package com.rcudev.simplemediaplayer.presenter

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import com.rcudev.player_service.connector.PlayerConnector
import com.rcudev.simplemediaplayer.domain.AppBusinessMedia
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class SimpleMediaViewModel : ViewModel() {

    private val playerConnector = PlayerConnector()

    private val _uiState = MutableStateFlow<UIState>(UIState.Initial)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {

            //todo reactivate
            /*getMediaStateUseCase().collect { mediaState ->
                when (mediaState) {
                    is PlayerMediaState.Buffering -> calculateProgressValues(mediaState.progress)
                    is PlayerMediaState.Initial -> _uiState.value = UIState.Initial
                    is PlayerMediaState.Playing -> {
                        //isPlaying = mediaState.isPlaying
                    }
                    is PlayerMediaState.Progress -> calculateProgressValues(mediaState.progress)
                    is PlayerMediaState.Ready -> {
                        //duration = mediaState.duration
                        _uiState.value = UIState.Ready
                    }
                }
            }*/
        }
    }

    fun loadTrackOne() {
        playerConnector.addItem(
            AppBusinessMedia(
                id = "idOne",
                albumTitle = "SoundHelix ONE",
                url = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3")
        ) { appBusinessMedia ->
            mapToMediaItem(appBusinessMedia)
        }
    }

    fun loadTrackTwo() {
        playerConnector.addItem(
            AppBusinessMedia(
                id = "idTwo",
                albumTitle = "SoundHelix TWO",
                url = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-2.mp3")
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

    /*fun onUIEvent(uiEvent: UIEvent) = viewModelScope.launch {
        when (uiEvent) {
            UIEvent.Backward -> setControlPlayerEventUseCase(PlayerControlEvent.Backward)
            UIEvent.Forward -> setControlPlayerEventUseCase(PlayerControlEvent.Forward)
            UIEvent.PlayPause -> setControlPlayerEventUseCase(PlayerControlEvent.PlayPause)
            is UIEvent.UpdateProgress -> {
                //progress = uiEvent.newProgress
                setControlPlayerEventUseCase(
                    PlayerControlEvent.UpdateProgress(
                        uiEvent.newProgress
                    )
                )
            }
        }
    }*/

    fun formatDuration(duration: Long): String {
        val minutes: Long = TimeUnit.MINUTES.convert(duration, TimeUnit.MILLISECONDS)
        val seconds: Long = (TimeUnit.SECONDS.convert(duration, TimeUnit.MILLISECONDS)
                - minutes * TimeUnit.SECONDS.convert(1, TimeUnit.MINUTES))
        return String.format("%02d:%02d", minutes, seconds)
    }

    private fun calculateProgressValues(currentProgress: Long) {
        //progress = if (currentProgress > 0) (currentProgress.toFloat() / duration) else 0f
        //progressString = formatDuration(currentProgress)
    }



    fun mapToMediaItem(appBusinessMedia: AppBusinessMedia): MediaItem {
        return MediaItem.Builder()
            .setMediaId(appBusinessMedia.id)
            .setUri(appBusinessMedia.url)
            .setMediaMetadata(
                MediaMetadata.Builder()
                    .setFolderType(MediaMetadata.FOLDER_TYPE_ALBUMS)
                    .setArtworkUri(Uri.parse("https://i.pinimg.com/736x/4b/02/1f/4b021f002b90ab163ef41aaaaa17c7a4.jpg"))
                    .setAlbumTitle(appBusinessMedia.albumTitle)
                    .setDisplayTitle("Song 1")
                    .build()
            ).build()
    }
}

sealed class UIEvent {
    object PlayPause : UIEvent()
    object Backward : UIEvent()
    object Forward : UIEvent()
    data class UpdateProgress(val newProgress: Float) : UIEvent()
}

sealed class UIState {
    object Initial : UIState()
    object Ready : UIState()
}