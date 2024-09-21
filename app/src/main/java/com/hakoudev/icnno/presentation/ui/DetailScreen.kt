package com.hakoudev.icnno.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.hakoudev.icnno.R
import com.hakoudev.icnno.presentation.event.HomeEvent
import com.hakoudev.icnno.presentation.state.HomeState
import com.hakoudev.icnno.presentation.ui.song.AsyncImageLoader
import com.hakoudev.icnno.presentation.ui.song.ScrollableText

@Composable
fun DetailScreen(state: HomeState, onEvent: (HomeEvent) -> Unit) {
    Scaffold(
        topBar = { DetailTopBar() },
        modifier = Modifier
            .fillMaxSize()
    ) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(30.dp))

            state.currentSong?.let {
                AsyncImageLoader(
                    uri = it.uri,
                    id = state.currentSong.id,
                    size = 350.dp
                )
                Spacer(modifier = Modifier.height(50.dp))

                ScrollableText(
                    text = state.currentSong.title,
                    fontSize = 5.em,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                )
                Text(
                    text = state.currentSong.artist,
                    fontWeight = FontWeight.Normal,
                    fontSize = 3.em,
                )
                Spacer(modifier = Modifier.height(50.dp))

                Slider(
                    modifier = Modifier.height(20.dp),
                    value = state.progress,
                    onValueChange = { newPos -> onEvent(HomeEvent.SeekTo(newPos)) }
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 7.dp)
                ) {
                    Text(state.progressTime)
                    Text(state.totalTime)
                }
                Spacer(modifier = Modifier.weight(1f))

                SongController(state, onEvent)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DetailTopBar() {
    TopAppBar(
        title = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Playing ...")
            }
        },
        modifier = Modifier.fillMaxWidth(),
    )
}

@Composable
fun SongController(state: HomeState, onEvent: (HomeEvent) -> Unit) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        IconButton(
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(MaterialTheme.colorScheme.primaryContainer),

            onClick = { onEvent(HomeEvent.SeekToPrevious) })
        {
            Icon(
                painter = painterResource(
                    R.drawable.previous
                ),
                contentDescription = "song play button",
                modifier = Modifier
                    .size(50.dp)
            )
        }

        Spacer(modifier = Modifier.width(10.dp))

        IconButton(
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(MaterialTheme.colorScheme.primaryContainer)
                .weight(1f),
            onClick = { onEvent(HomeEvent.PlayOrPause) })
        {
            Icon(
                painter = painterResource(
                    id = if (state.isPlaying) R.drawable.pause else
                        R.drawable.play
                ),
                contentDescription = "song play button",
                modifier = Modifier.size(50.dp)
            )
        }

        Spacer(modifier = Modifier.width(10.dp))
        IconButton(
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(MaterialTheme.colorScheme.primaryContainer),
            onClick = { onEvent(HomeEvent.SeekToNext) })
        {
            Icon(
                painter = painterResource(
                    id =
                    R.drawable.next
                ),
                contentDescription = "song play button",
                modifier = Modifier.size(50.dp)
            )
        }
    }
}


//@Preview
//@Composable
//fun PreviewDetailScreen() {
//    val state = HomeState(
//        totalTime = "00:00",
//        progressTime = "01:23",
//        progress = 0.42f,
//        isPlaying = true,
//        currentSong = AudioModel(
//            id = 1000008224,
//            title = "Fly me to the moon",
//            artist = "The Macroons Project",
//            uri = Uri.parse("content://media/external/audio/media/1000008224"),
//            image = null
//        )
//    )
//     DetailScreen(state = state, onEvent = {})
//}