package com.kalfian.stiki.stiki_e_appointment.models.appointmentResponse

import com.google.gson.annotations.SerializedName
import com.kalfian.stiki.stiki_e_appointment.models.Appointment
import com.kalfian.stiki.stiki_e_appointment.models.global.MetaResponse
import kotlinx.serialization.Serializable

@Serializable
data class GetAppointmentsResponse (
    @SerializedName("message") val message : String,
    @SerializedName("data") val data : ArrayList<Appointment>,
    @SerializedName("meta") val meta : MetaResponse
)