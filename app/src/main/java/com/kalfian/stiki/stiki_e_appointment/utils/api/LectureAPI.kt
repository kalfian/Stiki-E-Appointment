package com.kalfian.stiki.stiki_e_appointment.utils.api

import com.kalfian.stiki.stiki_e_appointment.models.activityResponse.GetActivityDetailResponse
import com.kalfian.stiki.stiki_e_appointment.models.activityResponse.GetActivityResponse
import com.kalfian.stiki.stiki_e_appointment.models.appointmentResponse.GetAppointmentDetailResponse
import com.kalfian.stiki.stiki_e_appointment.models.appointmentResponse.GetAppointmentsResponse
import com.kalfian.stiki.stiki_e_appointment.models.global.MessageResponse
import com.kalfian.stiki.stiki_e_appointment.models.logbookResponse.GetLogbookDetailResponse
import com.kalfian.stiki.stiki_e_appointment.models.requests.CreateLogbookRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface LectureAPI {
    @GET("v1/lecture/activity")
    fun getLectureActivity(): Call<GetActivityResponse>

    @GET("v1/lecture/activity/{id}")
    fun getLectureActivityDetail(
        @Path("id") id: Int,
        @Query("load_students") loadStudents: Boolean = false,
        @Query("load_lectures") loadLectures: Boolean = false
    ): Call<GetActivityDetailResponse>

    @GET("v1/lecture/activity/{id}/appointment")
    fun getLectureAppointmentsByActivity(
        @Path("id") activityId: Int,
        @Query("load_student") loadStudent: Boolean = false,
        @Query("load_lectures") loadLectures: Boolean = false
    )

    @GET("v1/lecture/appointment")
    fun getLectureAppointments(
        @Query("load_lectures") loadLectures: Boolean = false,
        @Query("load_student") loadStudent: Boolean = false,
        @Query("status") status: Int? = null,
        @Query("order_by") orderBy: String = "start_date",
        @Query("order_type") orderType: String = "desc",
        @Query("limit") limit: Int = 10,
        @Query("page") page: Int = 1,
        @Query("start_date") startDate: String? = null,
        @Query("end_date") endDate: String? = null,
        @Query("filter_now") filterNow: Int? = null
    ): Call<GetAppointmentsResponse>

    @GET("v1/lecture/appointment/{id}")
    fun getLectureAppointmentDetail(
        @Path("id") id: Int,
        @Query("load_students") loadStudents: Boolean = false,
        @Query("load_lectures") loadLectures: Boolean = false
    ): Call<GetAppointmentDetailResponse>

    //Logbook
    @POST("v1/lecture/activity/{id}/logbook")
    fun postLectureLogbook(
        @Path("id") activityId: Int,
        @Body request: CreateLogbookRequest
    ): Call<MessageResponse>

//    @PUT("v1/lecture/activity/{id}/logbook/{logbook_id}")
//    fun updateLectureLogbook(
//        @Path("id") activityId: Int,
//        @Path("logbook_id") logbookId: Int,
//        @Body request: UpdateLogbookLectureRequest
//    ): Call<MessageResponse>

    @GET("v1/lecture/activity/{id}/logbook/{logbook_id}")
    fun getLectureLogbookById(
        @Path("id") activityId: Int,
        @Path("logbook_id") logbookId: Int
    ): Call<GetLogbookDetailResponse>
}