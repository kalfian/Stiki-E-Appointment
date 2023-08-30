package com.kalfian.stiki.stiki_e_appointment.responses.loginResponse

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponseData (
    @SerializedName("token") val token : String,
    @SerializedName("user") val user : LoginResponseUser
)