package com.hakoudev.icnno.data.local

import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class PlayerService @Inject constructor(
    private val exoPlayer: ExoPlayer,
) : Player.Listener {

    private val _audioState: MutableStateFlow<AudioState> = MutableStateFlow(AudioState.Initial)
    val audioState: StateFlow<AudioState> = _audioState.asStateFlow()

    private var progressJob: Job? = null

    init {
        exoPlayer.addListener(this)
    }

    fun setMediaItems(mediaItems: List<MediaItem>) {
        exoPlayer.setMediaItems(mediaItems, true)
        exoPlayer.prepare()
    }

    override fun onPlaybackStateChanged(playbackState: Int) {
        super.onPlaybackStateChanged(playbackState)
        when (playbackState) {
            Player.STATE_BUFFERING -> _audioState.value =
                AudioState.Buffering(exoPlayer.currentPosition)

            Player.STATE_READY -> _audioState.value = AudioState.Ready(exoPlayer.duration)
        }
    }

    override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
        super.onMediaItemTransition(mediaItem, reason)

        _audioState.value = AudioState.CurrentPlaying(exoPlayer.currentMediaItemIndex)
    }

    private suspend fun startProgressUpdate() = progressJob.run {
        while (true) {
            delay(500)
            _audioState.value = AudioState.Progress(exoPlayer.currentPosition)
        }
    }

    private fun stopProgressUpdate() {
        progressJob?.cancel()
        _audioState.value = AudioState.Playing(isPlaying = false)
    }


    @OptIn(DelicateCoroutinesApi::class)
    fun playOrPause() {
        if (exoPlayer.isPlaying) {
            exoPlayer.pause()
            stopProgressUpdate()
        } else {
            exoPlayer.play()
            _audioState.value = AudioState.Playing(isPlaying = true)
            GlobalScope.launch(Dispatchers.Main) {
                startProgressUpdate()
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun selectAudioChange(selectedAudioIndex: Int) {
        _audioState.value = AudioState.CurrentPlaying(exoPlayer.currentMediaItemIndex)

        exoPlayer.seekToDefaultPosition(selectedAudioIndex)
        _audioState.value = AudioState.Playing(
            isPlaying = true
        )
        exoPlayer.playWhenReady = true
        GlobalScope.launch(Dispatchers.Main) {
            startProgressUpdate()
        }
    }

    fun seekTo(newPosition: Long) {
        exoPlayer.seekTo(newPosition)
    }

    fun seekToNext() {
        exoPlayer.seekToNextMediaItem()
    }

    fun seekToPrevious() {
        exoPlayer.seekToPreviousMediaItem()
    }

    fun release() {
        exoPlayer.release()
    }
}

sealed class AudioState {
    data object Initial : AudioState()
    data class Ready(val duration: Long) : AudioState()
    data class Progress(val progress: Long) : AudioState()
    data class Buffering(val progress: Long) : AudioState()
    data class Playing(val isPlaying: Boolean) : AudioState()
    data class CurrentPlaying(val mediaItemIndex: Int) : AudioState()
}
