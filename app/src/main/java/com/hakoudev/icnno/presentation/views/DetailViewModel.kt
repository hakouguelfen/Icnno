package com.hakoudev.icnno.presentation.views

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hakoudev.icnno.data.local.PlayerService
import com.hakoudev.icnno.presentation.event.HomeEvent
import com.hakoudev.icnno.presentation.state.HomeState
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailViewModel @Inject constructor(
    private val playerService: PlayerService,
) : ViewModel() {

    var state by mutableStateOf(HomeState())

    init {
        viewModelScope.launch {
        }
    }

    fun onEvent(event: HomeEvent) {
    }
}