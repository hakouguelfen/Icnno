package com.hakoudev.icnno.presentation.views

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import com.hakoudev.icnno.data.local.AudioState
import com.hakoudev.icnno.data.local.PlayerService
import com.hakoudev.icnno.data.model.AudioModel
import com.hakoudev.icnno.domain.repository.AudioRepository
import com.hakoudev.icnno.presentation.event.HomeEvent
import com.hakoudev.icnno.presentation.state.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val audioRepository: AudioRepository,
    private val playerService: PlayerService,
) : ViewModel() {
    var state by mutableStateOf(HomeState())

    init {
        loadAudioData()
    }

    init {
        viewModelScope.launch {
            playerService.audioState.collectLatest { audioState ->
                when (audioState) {
                    AudioState.Initial -> TODO()
                    is AudioState.CurrentPlaying -> {
                        state = state.copy(currentSong = state.audioList[audioState.mediaItemIndex])
                    }

                    is AudioState.Playing -> state = state.copy(isPlaying = audioState.isPlaying)
                    is AudioState.Buffering -> calculateProgressValue(audioState.progress)
                    is AudioState.Progress -> calculateProgressValue(audioState.progress)
                    is AudioState.Ready -> state = state.copy(
                        duration = audioState.duration,
                        totalTime = formatDuration(audioState.duration)
                    )
                }
            }
        }
    }

    private fun loadAudioData() {
        viewModelScope.launch {
            val songs = audioRepository.getAudioFiles()
            println(songs)

            state = state.copy(audioList = songs)
            state = state.copy(filteredAudioList = songs)
            setMediaItems()
        }
    }

    private fun setMediaItems() {
        state.audioList.map {
            audioTransformer(it)
        }.also {
            playerService.setMediaItems(it)
        }
    }

    private fun audioTransformer(audio: AudioModel): MediaItem {
        return MediaItem.Builder().setUri(audio.uri).setMediaMetadata(
            MediaMetadata.Builder()
                .setAlbumArtist(audio.artist)
                .setDisplayTitle(audio.title)
                .build()
        ).build()
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.Start -> start(event.audio)
            HomeEvent.PlayOrPause -> playerService.playOrPause()
            HomeEvent.SeekToPrevious -> playerService.seekToPrevious()
            HomeEvent.SeekToNext -> playerService.seekToNext()
            is HomeEvent.SeekTo -> {
                val newPosition = (event.position * state.duration).toLong()
                playerService.seekTo(newPosition)
            }

            is HomeEvent.Search -> search(event.query)
        }
    }

    private fun start(audio: AudioModel) {
        val currentSongIndex = state.audioList.indexOf(audio)
        playerService.selectAudioChange(currentSongIndex)
    }

    private fun calculateProgressValue(currentProgress: Long) {
        state = state.copy(
            progress =
            if (currentProgress > 0) (currentProgress.toFloat() / state.duration.toFloat())
            else 0f,

            progressTime = formatDuration(currentProgress)
        )
    }

    private fun formatDuration(duration: Long): String {
        val totalSeconds = TimeUnit.MILLISECONDS.toSeconds(duration)

        val minutes = TimeUnit.SECONDS.toMinutes(totalSeconds)
        val seconds = totalSeconds - TimeUnit.MINUTES.toSeconds(minutes)

        return String.format("%02d:%02d", minutes, seconds)
    }

    private fun search(query: String) {
        state = state.copy(
            filteredAudioList = state.audioList.filter {
                it.title.contains(query, ignoreCase = true)
            }
        )
    }

    override fun onCleared() {
        super.onCleared()
        playerService.release()
    }
}
