package com.hakoudev.icnno.di

import android.content.Context
import androidx.annotation.OptIn
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector
import androidx.media3.session.MediaSession
import com.hakoudev.icnno.data.local.AudioLocalDataSource
import com.hakoudev.icnno.data.local.AudioNotificationManager
import com.hakoudev.icnno.data.local.ContentResolver
import com.hakoudev.icnno.data.local.PlayerService
import com.hakoudev.icnno.data.repository.AudioRepositoryImpl
import com.hakoudev.icnno.domain.repository.AudioRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.UnstableApi
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AudioModule {
    @Provides
    @Singleton
    fun provideAudioAttributes(): AudioAttributes = AudioAttributes.Builder()
        .setContentType(C.AUDIO_CONTENT_TYPE_MOVIE)
        .setUsage(C.USAGE_MEDIA)
        .build()

    @OptIn(androidx.media3.common.util.UnstableApi::class)
    @Provides
    @Singleton
    @UnstableApi
    fun provideExoPlayer(
        @ApplicationContext context: Context,
        audioAttributes: AudioAttributes,
    ): ExoPlayer = ExoPlayer.Builder(context)
        .setAudioAttributes(audioAttributes, true)
        .setHandleAudioBecomingNoisy(true)
        .setTrackSelector(DefaultTrackSelector(context))
        .build()


    @Provides
    @Singleton
    fun provideMediaSession(
        @ApplicationContext context: Context,
        player: ExoPlayer,
    ): MediaSession = MediaSession.Builder(context, player).build()

    @Provides
    @Singleton
    fun provideNotificationManager(
        @ApplicationContext context: Context,
        player: ExoPlayer,
    ): AudioNotificationManager = AudioNotificationManager(
        context = context,
        exoPlayer = player
    )


    @Provides
    @Singleton
    fun providePlayerService(exoPlayer: ExoPlayer): PlayerService {
        return PlayerService(exoPlayer)
    }

    @Provides
    @Singleton
    fun provideAudioRepository(audioLocalDataSource: AudioLocalDataSource): AudioRepository {
        return AudioRepositoryImpl(audioLocalDataSource)
    }

    @Provides
    @Singleton
    fun provideAudioLocalDataSource(contentResolver: ContentResolver): AudioLocalDataSource {
        return AudioLocalDataSource(contentResolver)
    }
}