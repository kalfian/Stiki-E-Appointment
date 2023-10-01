package com.kalfian.stiki.stiki_e_appointment.models.activityResponse

import com.google.gson.annotations.SerializedName
import com.kalfian.stiki.stiki_e_appointment.models.Activity
import kotlinx.serialization.Serializable

@Serializable
data class GetActivityResponse (
    @SerializedName("message") val message : String,
    @SerializedName("data") val data : ArrayList<Activity>,
)