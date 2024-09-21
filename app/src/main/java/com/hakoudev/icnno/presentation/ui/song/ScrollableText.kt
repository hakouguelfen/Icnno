package com.hakoudev.icnno.presentation.ui.song

import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ScrollableText(text: String, fontSize: TextUnit, modifier: Modifier) {
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            while (true) {
                scrollState.scrollBy(1f)
                delay(50)
                if (scrollState.value == scrollState.maxValue) {
                    scrollState.scrollTo(0)
                }
            }
        }
    }

    Text(
        text,
        fontSize = fontSize,
        fontWeight = FontWeight.Bold,
        modifier = modifier.horizontalScroll(scrollState)
    )
}