package com.kalfian.stiki.stiki_e_appointment.models.appointmentResponse

import com.google.gson.annotations.SerializedName
import com.kalfian.stiki.stiki_e_appointment.models.Activity
import com.kalfian.stiki.stiki_e_appointment.models.Appointment
import kotlinx.serialization.Serializable

@Serializable
data class AppointmentDetailResponseData(
    @SerializedName("appointment") val appointment: Appointment,
    @SerializedName("activity") val activity: Activity,
    @SerializedName("participant") val participants: AppointmentParticipantData,
)