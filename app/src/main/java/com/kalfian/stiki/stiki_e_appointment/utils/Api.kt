package com.kalfian.stiki.stiki_e_appointment.utils

import com.kalfian.stiki.stiki_e_appointment.models.activity_response.GetActivityDetailResponse
import com.kalfian.stiki.stiki_e_appointment.models.activity_response.GetActivityResponse
import com.kalfian.stiki.stiki_e_appointment.requests.LoginRequest
import com.kalfian.stiki.stiki_e_appointment.models.global.MessageResponse
import com.kalfian.stiki.stiki_e_appointment.models.auth_response.LoginResponse
import com.kalfian.stiki.stiki_e_appointment.requests.CreateAppointmentRequest
import com.kalfian.stiki.stiki_e_appointment.utils.api.StudentAPI
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api : StudentAPI {
    @POST("v1/auth")
    fun postLogin(
        @Body credential: LoginRequest
    ): Call<LoginResponse>

    @POST("v1/auth/logout")
    fun postLogout(): Call<MessageResponse>

}