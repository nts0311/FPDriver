package com.sonnt.fpdriver.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.sonnt.fpdriver.R
import com.sonnt.fpdriver.network.NetworkModule
import com.sonnt.fpdriver.network.dto.request.UpdateFcmTokenRequest
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FcmService: FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if (remoteMessage.data.isNotEmpty()) {
            val title = remoteMessage.data["title"] ?: ""
            val content = remoteMessage.data["content"] ?: ""
            Log.d("FCM", "$title:$content")
            createNotification(title, content)
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        GlobalScope.launch {
            NetworkModule.authService.updateFcmToken(UpdateFcmTokenRequest(fcmToken = token))
        }
    }

    fun createNotification(title: String, content: String) {
        createNotificationChannel()
        var builder = NotificationCompat.Builder(this, "fpdriver_noti_channel")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(content)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(1, builder.build())
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "fpdriver_noti_channel"
            val descriptionText = "tung nui"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("fpdriver_noti_channel", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}