package com.hakoudev.icnno.data.repository

import com.hakoudev.icnno.data.local.AudioLocalDataSource
import com.hakoudev.icnno.data.model.AudioModel
import com.hakoudev.icnno.domain.repository.AudioRepository
import javax.inject.Inject

class AudioRepositoryImpl @Inject constructor(private val audioLocalDataSource: AudioLocalDataSource) :
    AudioRepository {
    override fun getAudioFiles(): List<AudioModel> {
        return audioLocalDataSource.getAudioFiles()
    }
}