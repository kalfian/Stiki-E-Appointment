package com.kalfian.stiki.stiki_e_appointment

import android.app.Application
import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import com.kalfian.stiki.stiki_e_appointment.utils.Constant
import com.kalfian.stiki.stiki_e_appointment.utils.SharedPreferenceUtil

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()

        // Init Firebase
        FirebaseApp.initializeApp(this)

        // Get if it logged in
        val token = SharedPreferenceUtil.retrieve(this, Constant.SHARED_TOKEN, "")
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val newToken = task.result
                Log.d("TESATESR1234", "New Token: $newToken")
            }
        }
    }
}
