package com.hakoudev.icnno.presentation.ui.song

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.hakoudev.icnno.presentation.event.HomeEvent
import com.hakoudev.icnno.presentation.state.HomeState

@Composable
fun Song(index: Int, state: HomeState, onEvent: (HomeEvent) -> Unit) {
    val audio = state.audioList[index]
    val songName = remember { mutableStateOf(audio.title) }
    val songArtist = remember { mutableStateOf(audio.artist) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onEvent(HomeEvent.Start(audio))
            }
            .padding(10.dp)
    ) {
        AsyncImageLoader(uri = audio.uri, id = audio.id, size = 80.dp)
        Spacer(modifier = Modifier.width(10.dp))
        Column {
            Text(
                text = songName.value,
                fontWeight = FontWeight.Bold,
                fontSize = 3.em,
                color = if (state.currentSong == audio) MaterialTheme.colorScheme.primary
                else MaterialTheme.typography.labelLarge.color
            )
            Text(text = songArtist.value, fontWeight = FontWeight.Medium, fontSize = 2.em)
        }
    }
}
