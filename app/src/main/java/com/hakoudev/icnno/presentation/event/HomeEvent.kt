package com.hakoudev.icnno.presentation.event

import com.hakoudev.icnno.data.model.AudioModel

sealed interface HomeEvent {
    data class Start(val audio: AudioModel) : HomeEvent
    data object PlayOrPause : HomeEvent
    data class SeekTo(val position: Float) : HomeEvent
    data object SeekToNext : HomeEvent
    data object SeekToPrevious : HomeEvent

    data class Search(val query: String) : HomeEvent
}