package com.kalfian.stiki.stiki_e_appointment.models.global

import com.google.gson.annotations.SerializedName

data class ErrorResponse (
    @SerializedName("message") val message : String? = null,
    @SerializedName("errors") val errors: Map<String, ArrayList<String>>? = null
)