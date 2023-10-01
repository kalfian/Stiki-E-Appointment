package com.kalfian.stiki.stiki_e_appointment.models.appointmentResponse

import com.google.gson.annotations.SerializedName
import com.kalfian.stiki.stiki_e_appointment.models.User
import kotlinx.serialization.Serializable

@Serializable
data class AppointmentParticipantData(
    @SerializedName("student") val student: User,
    @SerializedName("lecture") val lecture: User,
    @SerializedName("lecture2") val lecture2: User? = null,
)