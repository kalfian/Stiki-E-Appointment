package com.kalfian.stiki.stiki_e_appointment.responses

import com.google.gson.annotations.SerializedName

data class LoginResponse (
    @SerializedName("data") var message : String,
    @SerializedName("success") var success : Boolean
)