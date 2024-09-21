package com.hakoudev.icnno.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.hakoudev.icnno.presentation.event.HomeEvent
import com.hakoudev.icnno.presentation.state.HomeState
import com.hakoudev.icnno.presentation.ui.song.Song

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(state: HomeState, onEvent: (HomeEvent) -> Unit, navController: NavController) {
    var query by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            SearchBar(
                modifier = Modifier
                    .fillMaxWidth(),
                placeholder = {
                    Text("Search by name...")
                },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "search icon")
                },
                trailingIcon = {
                    if (active) {
                        Icon(
                            modifier = Modifier.clickable {
                                if (query.isNotEmpty()) {
                                    query = ""
                                } else {
                                    active = false
                                    navController.popBackStack()
                                }
                            },
                            imageVector = Icons.Default.Clear,
                            contentDescription = "clear icon"
                        )
                    }
                },
                query = query,
                onQueryChange = {
                    query = it
                    onEvent(HomeEvent.Search(query))
                },
                onSearch = {
                    active = false
                },
                active = active,
                onActiveChange = {
                    active = it
                },
            ) {
                LazyColumn {
                    items(
                        count = state.filteredAudioList.size,
                        key = { state.filteredAudioList[it].id },
                        itemContent = { Song(it, state, onEvent) }
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding))
    }
}