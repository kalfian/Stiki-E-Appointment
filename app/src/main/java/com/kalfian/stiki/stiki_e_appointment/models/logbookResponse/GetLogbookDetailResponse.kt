package com.kalfian.stiki.stiki_e_appointment.models.logbookResponse

import com.google.gson.annotations.SerializedName
import com.kalfian.stiki.stiki_e_appointment.models.Logbook
import com.kalfian.stiki.stiki_e_appointment.models.appointmentResponse.AppointmentDetailResponseData
import kotlinx.serialization.Serializable

@Serializable
data class GetLogbookDetailResponse (
    @SerializedName("message") val message : String,
    @SerializedName("data") val data : Logbook,
)