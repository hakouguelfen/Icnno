package com.hakoudev.icnno.domain.repository

import androidx.media3.common.MediaItem

interface PlayerRepository {
    fun setMediaItems(mediaItems: List<MediaItem>)
    fun play()
    fun pause()
    fun resume()
    fun seekTo(position: Long)
}