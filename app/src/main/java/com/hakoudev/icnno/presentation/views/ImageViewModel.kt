package com.hakoudev.icnno.presentation.views

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.net.Uri
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ImageViewModel @Inject constructor(
    @ApplicationContext val context: Context,
) : ViewModel() {
    private val _imagesCache = mutableStateMapOf<String, Bitmap?>()
    val imagesCache: Map<String, Bitmap?> get() = _imagesCache

    // Function to load image by URL and cache it
    suspend fun fetchImage(id: String, imageUri: Uri) {
        if (!_imagesCache.containsKey(id)) {
            val bitmap = getEmbeddedImage(imageUri)
            _imagesCache[id] = bitmap
        }
    }

    private suspend fun getEmbeddedImage(uri: Uri): Bitmap? {
        return withContext(Dispatchers.IO) {
            val retriever = MediaMetadataRetriever()

            try {
                retriever.setDataSource(context, uri)
                val art = retriever.embeddedPicture
                if (art != null) {
                    BitmapFactory.decodeByteArray(art, 0, art.size)
                } else {
                    null
                }
            } catch (e: Exception) {
                null
            } finally {
                retriever.release()
            }
        }
    }
}