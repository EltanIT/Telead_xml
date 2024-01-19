package com.example.telead_xml.domen.service.notification

import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.telead_xml.R
import com.example.telead_xml.domen.service.MainService

class NotificationService: android.app.Service() {
    override fun onBind(p0: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
         val notification = NotificationCompat.Builder(
            this.applicationContext,
            MainService().NOTIFICATION_CHANNEL_ID)
            .setContentTitle("ggg")
            .setContentText("fff")
            .setSmallIcon(R.drawable.ic_apple)
             .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()

        startForeground(1, notification)

        return START_NOT_STICKY
    }

    companion object{
        const val NOTIFICATION_ID = 101
    }
}