package com.kalfian.stiki.stiki_e_appointment.utils.interceptors

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response

class UnauthorizeInterceptor(private val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        if (response.code == 401) {
            // Show popup or dialog to prompt user to log in
            // You can use an AlertDialog or any other method to display the popup
            showLoginPopup(context)
        }

        return response
    }

    private fun showLoginPopup(context: Context) {
        // Implement your logic to show the login popup
    }
}