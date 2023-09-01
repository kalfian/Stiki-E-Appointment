package com.kalfian.stiki.stiki_e_appointment.responses

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class RoleResponse (
    @SerializedName("id") val id : Int = 0,
    @SerializedName("name") val name : String = "",
)