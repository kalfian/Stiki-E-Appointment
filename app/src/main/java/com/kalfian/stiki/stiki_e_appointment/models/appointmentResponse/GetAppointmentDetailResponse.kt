package com.kalfian.stiki.stiki_e_appointment.models.appointmentResponse

import com.google.gson.annotations.SerializedName
import com.kalfian.stiki.stiki_e_appointment.models.Activity
import com.kalfian.stiki.stiki_e_appointment.models.Appointment
import com.kalfian.stiki.stiki_e_appointment.models.User
import kotlinx.serialization.Serializable

@Serializable
data class GetAppointmentDetailResponse (
    @SerializedName("message") val message : String,
    @SerializedName("data") val data : AppointmentDetailResponseData,
)

@Serializable
data class AppointmentDetailResponseData(
    @SerializedName("appointment") val appointment: Appointment,
    @SerializedName("activity") val activity: Activity,
    @SerializedName("participant") val participants: AppointmentParticipantData,
)

@Serializable
data class AppointmentParticipantData(
    @SerializedName("student") val student: User,
    @SerializedName("lecture") val lecture: User,
    @SerializedName("lecture2") val lecture2: User? = null,
)