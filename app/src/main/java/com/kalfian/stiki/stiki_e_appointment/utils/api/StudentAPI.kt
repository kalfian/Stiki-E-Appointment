package com.kalfian.stiki.stiki_e_appointment.utils.api

import com.kalfian.stiki.stiki_e_appointment.models.activityResponse.GetActivityDetailResponse
import com.kalfian.stiki.stiki_e_appointment.models.activityResponse.GetActivityResponse
import com.kalfian.stiki.stiki_e_appointment.models.appointmentResponse.GetAppointmentDetailResponse
import com.kalfian.stiki.stiki_e_appointment.models.appointmentResponse.GetAppointmentsResponse
import com.kalfian.stiki.stiki_e_appointment.models.global.MessageResponse
import com.kalfian.stiki.stiki_e_appointment.models.logbookResponse.GetLogbookDetailResponse
import com.kalfian.stiki.stiki_e_appointment.models.logbookResponse.GetLogbooksResponse
import com.kalfian.stiki.stiki_e_appointment.models.requests.CreateAppointmentRequest
import com.kalfian.stiki.stiki_e_appointment.models.requests.CreateLogbookRequest
import com.kalfian.stiki.stiki_e_appointment.models.requests.UpdateLogbookStudentRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
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

    @GET("v1/student/activity/{id}/appointment")
    fun getStudentAppointmentsByActivity(
        @Path("id") activityId: Int,
        @Query("load_students") loadStudents: Boolean = false,
        @Query("load_lectures") loadLectures: Boolean = false
    )

    @GET("v1/student/appointment")
    fun getStudentAppointments(
        @Query("load_lectures") loadLectures: Boolean = false,
        @Query("status") status: Int? = null,
        @Query("order_by") orderBy: String = "start_date",
        @Query("order_type") orderType: String = "desc",
        @Query("limit") limit: Int = 10,
        @Query("page") page: Int = 1,
        @Query("start_date") startDate: String? = null,
        @Query("end_date") endDate: String? = null,
        @Query("filter_now") filterNow: Int? = null
    ): Call<GetAppointmentsResponse>

    @GET("v1/student/appointment/{id}")
    fun getStudentAppointmentDetail(
        @Path("id") id: Int,
        @Query("load_students") loadStudents: Boolean = false,
        @Query("load_lectures") loadLectures: Boolean = false
    ): Call<GetAppointmentDetailResponse>

    //Logbook
    @POST("v1/student/activity/{id}/logbook")
    fun postStudentLogbook(
        @Path("id") activityId: Int,
        @Body request: CreateLogbookRequest
    ): Call<MessageResponse>

    @GET("v1/student/activity/{id}/logbook")
    fun getStudentLogbookByActivity(
        @Path("id") activityId: Int,
    ): Call<GetLogbooksResponse>

    @PUT("v1/student/activity/{id}/logbook/{logbook_id}")
    fun updateStudentLogbook(
        @Path("id") activityId: Int,
        @Path("logbook_id") logbookId: Int,
        @Body request: UpdateLogbookStudentRequest
    ): Call<MessageResponse>

    @GET("v1/student/activity/{id}/logbook/{logbook_id}")
    fun getStudentLogbookById(
        @Path("id") activityId: Int,
        @Path("logbook_id") logbookId: Int
    ): Call<GetLogbookDetailResponse>
}