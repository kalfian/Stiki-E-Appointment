package com.kalfian.stiki.stiki_e_appointment

import android.app.Application
import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import com.kalfian.stiki.stiki_e_appointment.models.global.MessageResponse
import com.kalfian.stiki.stiki_e_appointment.utils.Constant
import com.kalfian.stiki.stiki_e_appointment.utils.Helper
import com.kalfian.stiki.stiki_e_appointment.utils.RetrofitClient
import com.kalfian.stiki.stiki_e_appointment.utils.SharedPreferenceUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()

        // Init Firebase
        FirebaseApp.initializeApp(this)

        // Get if it logged in
        val token = SharedPreferenceUtil.retrieve(this, Constant.SHARED_TOKEN, "")
        if(token != "") {
            val isLecture = SharedPreferenceUtil.retrieve(this, Constant.SHARED_IS_LECTURE, false)
            if (isLecture) {
                FirebaseMessaging.getInstance().subscribeToTopic("lecture")
            } else {
                FirebaseMessaging.getInstance().subscribeToTopic("student")
            }
            FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val newToken = task.result

                    RetrofitClient.callAuth(applicationContext).postFcmToken(
                        hashMapOf<String, String>().apply {
                            put("fcm_token", newToken)
                        }
                    ).enqueue(object:
                        Callback<MessageResponse> {
                        override fun onResponse(
                            call: Call<MessageResponse>,
                            response: Response<MessageResponse>
                        ) {

                        }

                        override fun onFailure(call: Call<MessageResponse>, t: Throwable) {

                        }

                    })

                    Log.d("TESATESR1234", "New Token: $newToken")
                }
            }
        }
    }
}
