package com.kalfian.stiki.stiki_e_appointment

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.kalfian.stiki.stiki_e_appointment.modules.AboutActivity
import com.kalfian.stiki.stiki_e_appointment.modules.SplashscreenActivity
import com.kalfian.stiki.stiki_e_appointment.modules.student.DashboardStudentActivity
import com.kalfian.stiki.stiki_e_appointment.utils.Constant

class MessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("TESATESR1234", "Refreshed Token: $token")
        // Send the token to your server or perform other actions as needed.
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d("TESATESR1234", "Message Notification Title outside")

        val notificationId = System.currentTimeMillis().toInt()
        createNotificationChannel()

        val i = Intent(this, SplashscreenActivity::class.java)
        i.putExtra("goToNotification", true)
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)

        val pendingIntent = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_IMMUTABLE)

        // Build the notification.
        val notificationBuilder = NotificationCompat.Builder(this, Constant.notification_channel_id)
            .setSmallIcon(R.drawable.stiki_splash)
            .setContentTitle("test")
            .setContentText("tsete2")
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        // Show the notification.
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(notificationId, notificationBuilder.build())

//        remoteMessage.notification?.let { notification ->
//            val title = notification.title ?: "Stiki E-Appointment"
//            val body = notification.body ?: "Ada notifikasi baru"
//
//            // Create a notification channel (for Android 8.0 and above).
//            createNotificationChannel()
//
//            Log.d("TESATESR1234", "Message Notification Title: $title")
//
//            // Create an intent to open your app or a specific activity when the notification is clicked.
//
//        }

    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            Constant.notification_channel_id,
            Constant.notification_channel_name,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }


}
