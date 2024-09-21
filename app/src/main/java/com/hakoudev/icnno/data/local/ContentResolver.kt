package com.hakoudev.icnno.data.local

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import com.hakoudev.icnno.data.model.AudioModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ContentResolver @Inject constructor(@ApplicationContext val context: Context) {
    fun getAudioFiles(): List<AudioModel> {
        val audioList = mutableListOf<AudioModel>()
        val collection = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI

        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST,
        )

        val selection = "${MediaStore.Audio.Media.IS_MUSIC} != 0"

        context.contentResolver.query(
            collection,
            projection,
            selection,
            null,
            null
        )?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
            val titleColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
            val artistColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)

            while (cursor.moveToNext()) {
                val id: Long = cursor.getLong(idColumn)
                val title: String = cursor.getString(titleColumn)
                val artist: String = cursor.getString(artistColumn)
                val uri: Uri = ContentUris.withAppendedId(
                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    id
                )

                val audio = AudioModel(id, title, artist, uri, image = null)
                audioList.add(audio)
            }
        }

        println(audioList)
        return audioList
    }

}