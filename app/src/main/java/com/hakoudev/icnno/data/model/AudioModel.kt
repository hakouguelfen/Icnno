package com.hakoudev.icnno.data.model

import android.graphics.Bitmap
import android.net.Uri

data class AudioModel(
    val id: Long,
    val title: String,
    val artist: String,
    val uri: Uri,
    val image: Bitmap?,
    //val name: String,
    //val data: String,
    //val duration: Int,
)