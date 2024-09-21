package com.hakoudev.icnno.presentation.state

import com.hakoudev.icnno.data.model.AudioModel

data class HomeState(
    val audioList: List<AudioModel> = emptyList(),
    val filteredAudioList: List<AudioModel> = emptyList(),
    val currentSong: AudioModel? = null,
    val isPlaying: Boolean = false,
    val progress: Float = 0f,
    val progressTime: String = "00:00",
    val totalTime: String = "00:00",
    val duration: Long = 0,
)
