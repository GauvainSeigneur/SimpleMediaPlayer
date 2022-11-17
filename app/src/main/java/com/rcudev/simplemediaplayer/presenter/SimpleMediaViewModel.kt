package com.rcudev.simplemediaplayer.presenter

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import com.rcudev.player_service.data.module.DomainModule
import com.rcudev.player_service.domain.usecase.GetMediaStateUseCase
import com.rcudev.player_service.domain.models.PlayerMediaState
import com.rcudev.player_service.domain.models.PlayerControlEvent
import com.rcudev.player_service.domain.usecase.AddItemInPlayerUseCase
import com.rcudev.player_service.domain.usecase.SetControlPlayerEventUseCase
import com.rcudev.player_service.domain.usecase.StartPlayerServiceUseCase
import com.rcudev.player_service.domain.usecase.StopPlayerServiceUseCase
import com.rcudev.simplemediaplayer.domain.AppBusinessMedia
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class SimpleMediaViewModel : ViewModel() {

    private val startPlayerServiceUseCase: StartPlayerServiceUseCase = DomainModule.getStartPlayerServiceUseCase()
    private val stopPlayerServiceUseCase: StopPlayerServiceUseCase = DomainModule.getStopPlayerServiceUseCase()
    private val addItemInPlayerUseCase: AddItemInPlayerUseCase<AppBusinessMedia> = DomainModule.getAddItemInPlayerUseCase()
    private val getMediaStateUseCase: GetMediaStateUseCase = DomainModule.getGetMediaStateUseCase()
    private val setControlPlayerEventUseCase: SetControlPlayerEventUseCase = DomainModule.getSetControlPlayerEventUseCase()

    private val _uiState = MutableStateFlow<UIState>(UIState.Initial)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            loadData()
            getMediaStateUseCase().collect { mediaState ->
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
            }
        }
    }

    override fun onCleared() {
        viewModelScope.launch {
            setControlPlayerEventUseCase(PlayerControlEvent.Stop)
        }
        stopPlayerServiceUseCase()
    }

    fun startService() {
        startPlayerServiceUseCase()
    }

    fun onUIEvent(uiEvent: UIEvent) = viewModelScope.launch {
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
    }

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

    private fun loadData() {
        addItemInPlayerUseCase(
            AppBusinessMedia(
                id = "id",
                albumTitle = "SoundHelix")
        ) { appBusinessMedia ->
            mapToMediaItem(appBusinessMedia)
        }
    }


    fun mapToMediaItem(appBusinessMedia: AppBusinessMedia): MediaItem {
        return MediaItem.Builder()
            .setMediaId(appBusinessMedia.id)
            .setUri("https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3")
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