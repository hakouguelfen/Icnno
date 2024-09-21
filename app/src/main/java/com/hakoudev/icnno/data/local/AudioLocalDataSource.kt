package com.hakoudev.icnno.data.local

import com.hakoudev.icnno.data.model.AudioModel
import javax.inject.Inject

class AudioLocalDataSource @Inject constructor(private val contentResolver: ContentResolver) {
    fun getAudioFiles(): List<AudioModel> {
        return contentResolver.getAudioFiles()
    }
}