package com.hakoudev.icnno.data.repository

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.hakoudev.icnno.domain.repository.PlayerRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PlayerRepositoryImpl @Inject constructor(@ApplicationContext val context: Context) :
    PlayerRepository {

    val exoPlayer = ExoPlayer.Builder(context).build()

    override fun setMediaItems(mediaItems: List<MediaItem>) {
        exoPlayer.setMediaItems(mediaItems, true)
        exoPlayer.prepare()
    }

    override fun play() {
        TODO("Not yet implemented")
    }

    override fun pause() {
        TODO("Not yet implemented")
    }

    override fun resume() {
        TODO("Not yet implemented")
    }

    override fun seekTo(position: Long) {
        TODO("Not yet implemented")
    }
}