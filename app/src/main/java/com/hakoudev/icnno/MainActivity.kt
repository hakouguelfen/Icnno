package com.hakoudev.icnno

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.app.ActivityCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hakoudev.icnno.data.local.AudioService
import com.hakoudev.icnno.presentation.ui.DetailScreen
import com.hakoudev.icnno.presentation.ui.HomeScreen
import com.hakoudev.icnno.presentation.ui.SearchScreen
import com.hakoudev.icnno.presentation.views.HomeViewModel
import com.hakoudev.icnno.ui.theme.IcnnoTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private var isServiceRunning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        ActivityCompat.requestPermissions(
            this,
            arrayOf("android.permission.READ_EXTERNAL_STORAGE"),
            0
        )

        setContent {
            IcnnoTheme {
                val navController = rememberNavController()
                val viewModel = hiltViewModel<HomeViewModel>()
                NavHost(navController = navController, startDestination = Home) {
                    composable<Home> {
                        startService()
                        HomeScreen(
                            state = viewModel.state,
                            onEvent = viewModel::onEvent,
                            navController
                        )
                    }
                    composable<Detail> {
                        DetailScreen(
                            state = viewModel.state,
                            onEvent = viewModel::onEvent,
                        )
                    }
                    composable<Search> {
                        SearchScreen(
                            state = viewModel.state,
                            onEvent = viewModel::onEvent,
                            navController
                        )
                    }
                }
            }
        }
    }

    private fun startService() {
        if (!isServiceRunning) {
            val intent = Intent(this, AudioService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(intent)
            } else {
                startService(intent)
            }
            isServiceRunning = true
        }
    }
}

@Serializable
object Home

@Serializable
object Detail

@Serializable
object Search
