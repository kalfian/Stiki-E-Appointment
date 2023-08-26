package com.kalfian.stiki.stiki_e_appointment.utils.interceptors

import android.content.Context
import com.kalfian.stiki.stiki_e_appointment.utils.Constant
import com.kalfian.stiki.stiki_e_appointment.utils.KeystoreUtils
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val keyStoreUtil = KeystoreUtils(context)
        var token = keyStoreUtil.retrieveKey(Constant.KEYSTORE_TOKEN)

        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()

        return chain.proceed(request)
    }
}
