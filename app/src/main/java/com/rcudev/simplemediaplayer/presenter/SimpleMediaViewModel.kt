package com.rcudev.simplemediaplayer.presenter

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import com.rcudev.player_service.domain.usecase.GetMediaStateUseCase
import com.rcudev.player_service.domain.models.RfMedia
import com.rcudev.player_service.domain.models.RfMediaState
import com.rcudev.player_service.domain.models.RfPlayerControlEvent
import com.rcudev.player_service.domain.usecase.AddItemInPlayerUseCase
import com.rcudev.player_service.domain.usecase.SetControlPlayerEventUseCase
import com.rcudev.player_service.domain.usecase.StartPlayerServiceUseCase
import com.rcudev.player_service.domain.usecase.StopPlayerServiceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@OptIn(SavedStateHandleSaveableApi::class)
@HiltViewModel
class SimpleMediaViewModel @Inject constructor(
    private val startPlayerServiceUseCase: StartPlayerServiceUseCase,
    private val stopPlayerServiceUseCase: StopPlayerServiceUseCase,
    private val addItemInPlayerUseCase: AddItemInPlayerUseCase,
    private val getMediaStateUseCase: GetMediaStateUseCase,
    private val setControlPlayerEventUseCase: SetControlPlayerEventUseCase,
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
            getMediaStateUseCase().collect { mediaState ->
                when (mediaState) {
                    is RfMediaState.Buffering -> calculateProgressValues(mediaState.progress)
                    is RfMediaState.Initial -> _uiState.value = UIState.Initial
                    is RfMediaState.Playing -> isPlaying = mediaState.isPlaying
                    is RfMediaState.Progress -> calculateProgressValues(mediaState.progress)
                    is RfMediaState.Ready -> {
                        duration = mediaState.duration
                        _uiState.value = UIState.Ready
                    }
                }
            }
        }
    }

    override fun onCleared() {
        viewModelScope.launch {
            setControlPlayerEventUseCase(RfPlayerControlEvent.Stop)
        }
        stopPlayerServiceUseCase()
    }

    fun startService() {
        startPlayerServiceUseCase()
    }

    fun onUIEvent(uiEvent: UIEvent) = viewModelScope.launch {
        when (uiEvent) {
            UIEvent.Backward -> setControlPlayerEventUseCase(RfPlayerControlEvent.Backward)
            UIEvent.Forward -> setControlPlayerEventUseCase(RfPlayerControlEvent.Forward)
            UIEvent.PlayPause -> setControlPlayerEventUseCase(RfPlayerControlEvent.PlayPause)
            is UIEvent.UpdateProgress -> {
                progress = uiEvent.newProgress
                setControlPlayerEventUseCase(
                    RfPlayerControlEvent.UpdateProgress(
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
        addItemInPlayerUseCase(RfMedia(
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