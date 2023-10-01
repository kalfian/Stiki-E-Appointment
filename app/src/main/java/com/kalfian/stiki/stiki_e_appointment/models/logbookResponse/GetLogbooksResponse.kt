package com.kalfian.stiki.stiki_e_appointment.models.logbookResponse

import com.google.gson.annotations.SerializedName
import com.kalfian.stiki.stiki_e_appointment.models.Logbook
import com.kalfian.stiki.stiki_e_appointment.models.global.MetaResponse
import kotlinx.serialization.Serializable

@Serializable
data class GetLogbooksResponse (
    @SerializedName("message") val message : String,
    @SerializedName("data") val data : ArrayList<Logbook>,
    @SerializedName("meta") val meta : MetaResponse
)