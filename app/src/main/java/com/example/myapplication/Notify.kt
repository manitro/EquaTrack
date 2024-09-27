package com.example.myapplication

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build


class Notify : Application() {


    override fun onCreate() {
        super.onCreate()
        channel_build()
    }

    fun channel_build() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //val name = getString(R.string.channel_name) once we understand, we use data
            //val descriptionText = getString(R.string.channel_description)
            val importance =
                NotificationManager.IMPORTANCE_DEFAULT //setPriority equivalent for android 8.0+
            val channel = NotificationChannel(
                "reminder_channel",
                "Reminder Notifications",
                importance
            ).apply { //this is the channel to pass
                description = "just the notification tested"
            }


//we register the channel
            val notifManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notifManager.createNotificationChannel(channel)

//if you want the notification to open an activity:
        }

    }
}
