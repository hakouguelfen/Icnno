package com.hakoudev.icnno.presentation.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.navigation.NavController
import com.hakoudev.icnno.Detail
import com.hakoudev.icnno.R
import com.hakoudev.icnno.Search
import com.hakoudev.icnno.presentation.event.HomeEvent
import com.hakoudev.icnno.presentation.state.HomeState
import com.hakoudev.icnno.presentation.ui.song.AsyncImageLoader
import com.hakoudev.icnno.presentation.ui.song.ScrollableText
import com.hakoudev.icnno.presentation.ui.song.Song

@Composable
fun HomeScreen(state: HomeState, onEvent: (HomeEvent) -> Unit, navController: NavController) {
    Scaffold(
        topBar = { TopBar(navController) },
        bottomBar = { BottomBar(state, onEvent, navController) },
        modifier = Modifier
            .fillMaxSize()
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            items(
                count = state.audioList.size,
                key = { state.audioList[it].id },
                itemContent = { Song(it, state, onEvent) }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavController) {
    TopAppBar(
        title = {
            Image(
                painter = painterResource(
                    id = R.drawable.logo
                ),
                contentDescription = "application logo",
                modifier = Modifier.size(50.dp)
            )
        },
        actions = {
            IconButton(
                onClick = {
                    navController.navigate(Search)
                }) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "song play button",
                    modifier = Modifier.size(30.dp)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
    )
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun BottomBar(state: HomeState, onEvent: (HomeEvent) -> Unit, navController: NavController) {
    if (state.currentSong == null) {
        return
    }

    BottomAppBar(
        modifier = Modifier
            .padding(10.dp)
            .clip(RoundedCornerShape(10.dp))
            .height(80.dp)
            .clickable {
                navController.navigate(Detail)
            }
    ) {
        Row(
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp)
        ) {
            AsyncImageLoader(
                uri = state.currentSong.uri,
                id = state.currentSong.id,
                size = 50.dp
            )

            Spacer(modifier = Modifier.width(10.dp))
            Column {
                ScrollableText(
                    text = state.currentSong.title,
                    fontSize = 2.em,
                    modifier = Modifier.width(240.dp)
                )

                Text(
                    text = state.currentSong.artist,
                    fontWeight = FontWeight.Medium,
                    fontSize = 1.5.em
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            IconButton(
                onClick = {
                    onEvent(HomeEvent.PlayOrPause)
                }) {
                Icon(
                    painter = painterResource(
                        id = if (state.isPlaying) R.drawable.pause else
                            R.drawable.play
                    ),
                    contentDescription = "song play button",
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    }
}