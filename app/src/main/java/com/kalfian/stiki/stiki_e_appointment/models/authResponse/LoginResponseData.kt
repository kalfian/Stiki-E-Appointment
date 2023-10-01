package com.kalfian.stiki.stiki_e_appointment.models.authResponse

import com.google.gson.annotations.SerializedName
import com.kalfian.stiki.stiki_e_appointment.models.User
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponseData (
    @SerializedName("token") val token : String,
    @SerializedName("user") val user : User
)