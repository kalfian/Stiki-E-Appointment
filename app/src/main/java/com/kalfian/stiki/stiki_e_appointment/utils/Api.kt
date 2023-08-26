package com.kalfian.stiki.stiki_e_appointment.utils

import com.kalfian.stiki.stiki_e_appointment.responses.LoginResponse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Body

interface Api {
    @POST("v1/auth")
    fun postLogin(
        @Body requestBody: RequestBody
    ): Call<LoginResponse>
}