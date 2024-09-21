package com.hakoudev.icnno

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        println("oncreatejk")

        //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        //    val name = getString(R.string.app_name)
        //    val descriptionText = getString(R.string.app_name)
        //    val importance = NotificationManager.IMPORTANCE_DEFAULT
        //    val channel = NotificationChannel("1", name, importance).apply {
        //        description = descriptionText
        //    }
        //
        //    // Register the channel with the system.
        //    val notificationManager: NotificationManager =
        //        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        //    notificationManager.createNotificationChannel(channel)
        //}
    }
}
