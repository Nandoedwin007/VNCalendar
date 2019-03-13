package com.example.vncalendar

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
//import android.support.v4.app.NotificationCompat
import androidx.core.app.NotificationCompat

//import sun.awt.image.SurfaceManager.getManager


class AlertReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val notificationHelper = NotificationHelper(context)
        val nb = notificationHelper.channelNotification
        notificationHelper.manager!!.notify(1, nb.build())
    }
}