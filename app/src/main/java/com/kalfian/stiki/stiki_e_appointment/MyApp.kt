package com.kalfian.stiki.stiki_e_appointment

import android.app.Application
import com.onesignal.OneSignal

const val ONESIGNAL_APP_ID = "b04375d0-69f3-405d-8225-884772d8bdf1"

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()

        // Logging set to help debug issues, remove before releasing your app.
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)

        // OneSignal Initialization
        OneSignal.initWithContext(this)
        OneSignal.setAppId(ONESIGNAL_APP_ID)
    }
}
