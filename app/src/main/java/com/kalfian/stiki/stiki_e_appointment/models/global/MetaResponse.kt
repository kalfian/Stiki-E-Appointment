package com.kalfian.stiki.stiki_e_appointment.models.global

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class MetaResponse (
    @SerializedName("total") val total : Int = 0,
    @SerializedName("current_page") val CurrentPage : Int = 0,
    @SerializedName("per_page") val perPage : Int = 0,
    @SerializedName("last_age") val lastPage : Int = 0,
)