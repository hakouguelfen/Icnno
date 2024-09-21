package com.hakoudev.icnno.domain.repository

import com.hakoudev.icnno.data.model.AudioModel

interface AudioRepository {
    fun getAudioFiles():List<AudioModel>
}