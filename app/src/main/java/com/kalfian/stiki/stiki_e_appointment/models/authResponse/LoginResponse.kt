package com.kalfian.stiki.stiki_e_appointment.models.authResponse

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse (
    @SerializedName("message") val message : String,
    @SerializedName("data") val data : LoginResponseData,
)




