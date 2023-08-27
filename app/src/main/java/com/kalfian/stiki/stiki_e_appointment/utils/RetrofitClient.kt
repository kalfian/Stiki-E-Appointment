package com.kalfian.stiki.stiki_e_appointment.utils

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.kalfian.stiki.stiki_e_appointment.BuildConfig
import com.kalfian.stiki.stiki_e_appointment.utils.interceptors.AuthInterceptor
import com.kalfian.stiki.stiki_e_appointment.utils.interceptors.UnauthorizeInterceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL: String = BuildConfig.BASE_URL

    fun call(): Api {
        val interceptor : HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }
        val client : OkHttpClient = OkHttpClient.Builder().apply {
            this.addInterceptor(interceptor)
        }.build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        return retrofit.create(Api::class.java)
    }

    fun callAuth(context: Context): Api {
        val interceptor : HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }

        val client : OkHttpClient = OkHttpClient.Builder().apply {
            this.addInterceptor(interceptor)
            this.addInterceptor(AuthInterceptor(context))
            this.addInterceptor(UnauthorizeInterceptor(context))
        }.build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        return retrofit.create(Api::class.java)
    }

    inline fun <reified T : Any> mapJsonToDataClass(response: ResponseBody?): T? {
        return try {
            val jsonString = response?.charStream()?.readText().toString()
            val gson = Gson()
            gson.fromJson(jsonString, T::class.java)
        } catch (e: Exception) {
            null
        }
    }


}