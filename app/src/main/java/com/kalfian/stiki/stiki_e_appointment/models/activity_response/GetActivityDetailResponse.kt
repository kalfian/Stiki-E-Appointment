package com.kalfian.stiki.stiki_e_appointment.models.activity_response

import com.google.gson.annotations.SerializedName
import com.kalfian.stiki.stiki_e_appointment.models.Activity
import kotlinx.serialization.Serializable

@Serializable
data class GetActivityDetailResponse (
    @SerializedName("message") val message : String,
    @SerializedName("data") val data : Activity,
)