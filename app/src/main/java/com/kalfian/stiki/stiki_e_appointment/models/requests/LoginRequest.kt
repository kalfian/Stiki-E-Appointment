package com.kalfian.stiki.stiki_e_appointment.models.requests

import com.google.gson.annotations.SerializedName

data class LoginRequest (
    @SerializedName("email") var email : String,
    @SerializedName("password") var password : String
)