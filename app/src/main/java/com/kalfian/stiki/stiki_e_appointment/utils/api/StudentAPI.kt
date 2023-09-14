package com.kalfian.stiki.stiki_e_appointment.utils.api

import com.kalfian.stiki.stiki_e_appointment.models.activity_response.GetActivityDetailResponse
import com.kalfian.stiki.stiki_e_appointment.models.activity_response.GetActivityResponse
import com.kalfian.stiki.stiki_e_appointment.models.global.MessageResponse
import com.kalfian.stiki.stiki_e_appointment.requests.CreateAppointmentRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface StudentAPI {
    @GET("v1/student/activity")
    fun getStudentActivity(): Call<GetActivityResponse>

    @GET("v1/student/activity/{id}")
    fun getStudentActivityDetail(
        @Path("id") id: Int,
        @Query("load_students") loadStudents: Boolean = false,
        @Query("load_lectures") loadLectures: Boolean = false
    ): Call<GetActivityDetailResponse>

    @POST("v1/student/activity/{id}/appointment")
    fun postStudentAppointment(
        @Path("id") activityId: Int,
        @Body request: CreateAppointmentRequest
    ): Call<MessageResponse>

    @GET("v1/student/appointment")
    fun getStudentAppointment(
        @Path("id") activityId: Int,
        @Query("load_students") loadStudents: Boolean = false,
        @Query("load_lectures") loadLectures: Boolean = false
    )
}