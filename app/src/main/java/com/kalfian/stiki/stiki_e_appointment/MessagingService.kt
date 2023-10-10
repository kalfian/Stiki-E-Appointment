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
import com.kalfian.stiki.stiki_e_appointment.models.global.MessageResponse
import com.kalfian.stiki.stiki_e_appointment.modules.AboutActivity
import com.kalfian.stiki.stiki_e_appointment.modules.SplashscreenActivity
import com.kalfian.stiki.stiki_e_appointment.modules.student.DashboardStudentActivity
import com.kalfian.stiki.stiki_e_appointment.utils.Constant
import com.kalfian.stiki.stiki_e_appointment.utils.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("TOKEN", token)
        // Update Token
        RetrofitClient.callAuth(applicationContext).postFcmToken(hashMapOf<String, String>().apply {
            put("fcm_token", token)
        }).enqueue(object:
            Callback<MessageResponse> {
            override fun onResponse(
                call: Call<MessageResponse>,
                response: Response<MessageResponse>
            ) {

            }

            override fun onFailure(call: Call<MessageResponse>, t: Throwable) {

            }

        })
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        val data = remoteMessage.data
        val title = data["title"] ?: "Stiki E-Appointment"
        val body = data["body"] ?: "Ada notifikasi baru"
        val intentTo = data["intentTo"]
        val intetId = data["intentId"]?.toInt() ?: 0

        val notificationId = System.currentTimeMillis().toInt()
        createNotificationChannel()

        sendNotification(title, body, notificationId)
    }

    private fun sendNotification(title: String, body: String, notificationId: Int) {
        val i = Intent(this, SplashscreenActivity::class.java)
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)

        val pendingIntent = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_IMMUTABLE)

        // Build the notification.
        val notificationBuilder = NotificationCompat.Builder(this, Constant.notification_channel_id)
            .setSmallIcon(R.drawable.stiki_splash)
            .setContentTitle(title)
            .setContentText(body)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        // Show the notification.
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(notificationId, notificationBuilder.build())
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
