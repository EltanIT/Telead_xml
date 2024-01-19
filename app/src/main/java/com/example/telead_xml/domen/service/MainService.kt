package com.example.telead_xml.domen.service

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build

class MainService: Application() {
    val NOTIFICATION_CHANNEL_ID = "notificationServiceChannel"

    override fun onCreate() {
        createNotificationChannel()
        super.onCreate()
    }

    private fun createNotificationChannel(){
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
             val notificationChannel = NotificationChannel(NOTIFICATION_CHANNEL_ID, "notification", NotificationManager.IMPORTANCE_DEFAULT)
             val notificationManager = getSystemService(NotificationManager::class.java)
             notificationManager.createNotificationChannel(notificationChannel)
        }
    }

}