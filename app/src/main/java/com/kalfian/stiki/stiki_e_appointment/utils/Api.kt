package com.kalfian.stiki.stiki_e_appointment.utils

import com.kalfian.stiki.stiki_e_appointment.models.requests.LoginRequest
import com.kalfian.stiki.stiki_e_appointment.models.global.MessageResponse
import com.kalfian.stiki.stiki_e_appointment.models.authResponse.LoginResponse
import com.kalfian.stiki.stiki_e_appointment.utils.api.LectureAPI
import com.kalfian.stiki.stiki_e_appointment.utils.api.StudentAPI
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Body

interface Api : StudentAPI, LectureAPI {
    @POST("v1/auth")
    fun postLogin(
        @Body credential: LoginRequest
    ): Call<LoginResponse>

    @POST("v1/auth/logout")
    fun postLogout(): Call<MessageResponse>

    @POST("v1/user/fcm-token")
    fun postFcmToken(
        @Body request: Map<String, String>
    ): Call<MessageResponse>

}