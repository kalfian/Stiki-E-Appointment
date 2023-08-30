package com.kalfian.stiki.stiki_e_appointment.responses.loginResponse

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponseUser (
    @SerializedName("id") val id : Int,
    @SerializedName("name") val name : String,
    @SerializedName("email") val email : String,
    @SerializedName("role") val role : LoginResponseRole
)