package com.rcudev.simplemediaplayer.presenter

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import com.rcudev.player_service.domain.RfMedia
import com.rcudev.player_service.models.MediaState
import com.rcudev.player_service.models.PlayerControlEvent
import com.rcudev.player_service.handler.PlayerControlEventHandler
import com.rcudev.player_service.service.PlayerPreparator
import com.rcudev.player_service.handler.PlayerStateHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@OptIn(SavedStateHandleSaveableApi::class)
@HiltViewModel
class SimpleMediaViewModel @Inject constructor(
    private val playerPreparator: PlayerPreparator,
    private val playerStateHandler: PlayerStateHandler,
    private val playerControlEventHandler: PlayerControlEventHandler,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var duration by savedStateHandle.saveable { mutableStateOf(0L) }
    var progress by savedStateHandle.saveable { mutableStateOf(0f) }
    var progressString by savedStateHandle.saveable { mutableStateOf("00:00") }
    var isPlaying by savedStateHandle.saveable { mutableStateOf(false) }

    private val _uiState = MutableStateFlow<UIState>(UIState.Initial)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            loadData()

            playerStateHandler.simpleMediaState.collect { mediaState ->
                when (mediaState) {
                    is MediaState.Buffering -> calculateProgressValues(mediaState.progress)
                    is MediaState.Initial -> _uiState.value = UIState.Initial
                    is MediaState.Playing -> isPlaying = mediaState.isPlaying
                    is MediaState.Progress -> calculateProgressValues(mediaState.progress)
                    is MediaState.Ready -> {
                        duration = mediaState.duration
                        _uiState.value = UIState.Ready
                    }
                }
            }
        }
    }

    override fun onCleared() {
        viewModelScope.launch {
            playerControlEventHandler.onPlayerEvent(PlayerControlEvent.Stop)
        }
    }

    fun startService(context: Context) {
        playerPreparator.startPlayerService(context)
    }

    fun stopService(context: Context) {
        playerPreparator.stopPlayerService(context)
    }

    fun onUIEvent(uiEvent: UIEvent) = viewModelScope.launch {
        when (uiEvent) {
            UIEvent.Backward -> playerControlEventHandler.onPlayerEvent(PlayerControlEvent.Backward)
            UIEvent.Forward -> playerControlEventHandler.onPlayerEvent(PlayerControlEvent.Forward)
            UIEvent.PlayPause -> playerControlEventHandler.onPlayerEvent(PlayerControlEvent.PlayPause)
            is UIEvent.UpdateProgress -> {
                progress = uiEvent.newProgress
                playerControlEventHandler.onPlayerEvent(
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
        progress = if (currentProgress > 0) (currentProgress.toFloat() / duration) else 0f
        progressString = formatDuration(currentProgress)
    }

    private fun loadData() {
        playerPreparator.addMediaItem(RfMedia(
            id = "id",
            albumTitle = "SoundHelix"
        ))
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