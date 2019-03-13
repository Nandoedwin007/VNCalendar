package com.example.vncalendar

import android.annotation.TargetApi
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.ContextWrapper
import android.os.Build
//import android.support.v4.app.NotificationCompat
import androidx.core.app.NotificationCompat



class NotificationHelper(base: Context) : ContextWrapper(base) {

    private var mManager: NotificationManager? = null

    val manager: NotificationManager?
        get() {
            if (mManager == null) {
                mManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            }

            return mManager
        }

    val channelNotification: NotificationCompat.Builder
        get() = NotificationCompat.Builder(applicationContext, channelID).setContentTitle("Alarm!").setContentText("Your AlarmManager is working.")
            .setSmallIcon(R.drawable.notification_template_icon_low_bg) //ic_android

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel()
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private fun createChannel() {
        val channel = NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH)

        manager!!.createNotificationChannel(channel)
    }

    companion object {
        val channelID = "channelID"
        val channelName = "Channel Name"
    }
}