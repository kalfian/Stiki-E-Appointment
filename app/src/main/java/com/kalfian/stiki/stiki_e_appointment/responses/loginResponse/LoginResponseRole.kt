package com.kalfian.stiki.stiki_e_appointment.responses.loginResponse

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponseRole (
    @SerializedName("id") val id : Int,
    @SerializedName("name") val name : String,
)