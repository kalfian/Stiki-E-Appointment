package com.kalfian.stiki.stiki_e_appointment.utils

import com.kalfian.stiki.stiki_e_appointment.requests.LoginRequest
import com.kalfian.stiki.stiki_e_appointment.responses.LoginResponse
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Body

interface Api {
    @POST("v1/auth")
    fun postLogin(
        @Body credential: LoginRequest
    ): Call<LoginResponse>
}