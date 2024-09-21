package com.hakoudev.icnno.data.local

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.hakoudev.icnno.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NotificationService @Inject constructor(@ApplicationContext val context: Context) {

    fun showNotification() {
        val notificationManager: NotificationManagerCompat =
            NotificationManagerCompat.from(context)

        val builder = NotificationCompat.Builder(context, "1")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("My notification")
            .setContentText("Hello World!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()

        //notificationManager.notify(1, builder)
    }
}