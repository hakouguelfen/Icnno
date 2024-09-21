package com.hakoudev.icnno.presentation.ui.song

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hakoudev.icnno.R
import com.hakoudev.icnno.presentation.views.ImageViewModel
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun AsyncImageLoader(uri: Uri, id: Long, size: Dp) {
    val viewModel = hiltViewModel<ImageViewModel>()

    val bitmap: Bitmap? = viewModel.imagesCache[id.toString()]
    val coroutineScope = rememberCoroutineScope()

    if (bitmap != null) {
        return DisplayImage(bitmap.asImageBitmap(), size)
    }

    Box {
        LoadingIndicator(size)
        coroutineScope.launch {
            viewModel.fetchImage(id.toString(), uri)
        }
    }
}

@Composable
fun LoadingIndicator(size: Dp) {
    Image(
        painter = painterResource(id = R.drawable.song),
        contentDescription = "Default Song Image",
        modifier = Modifier
            .size(size)
            .clip(RoundedCornerShape(20))
            .background(Color.Gray)
    )
}

@Composable
fun DisplayImage(image: ImageBitmap, size: Dp) {
    Image(
        bitmap = image,
        contentDescription = "Song Image",
        modifier = Modifier
            .size(size)
            .clip(RoundedCornerShape(20))
    )
}
