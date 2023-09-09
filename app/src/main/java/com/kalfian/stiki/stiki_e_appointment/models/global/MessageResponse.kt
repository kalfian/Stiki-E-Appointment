package com.kalfian.stiki.stiki_e_appointment.models.global

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class MessageResponse (
    @SerializedName("message") val message : String = "",
)