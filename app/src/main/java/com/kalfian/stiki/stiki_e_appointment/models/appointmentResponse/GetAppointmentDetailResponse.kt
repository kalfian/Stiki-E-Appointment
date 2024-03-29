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