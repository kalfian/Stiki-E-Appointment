package com.kalfian.stiki.stiki_e_appointment.responses

import com.google.gson.annotations.SerializedName

data class LoginResponse (
    @SerializedName("message") var message : String,
    @SerializedName("data") var data : LoginResponseData,
)

data class LoginResponseData (
    @SerializedName("token") var token : String,
    @SerializedName("user") var user : LoginResponseUser
)

data class LoginResponseUser (
    @SerializedName("id") var id : Int,
    @SerializedName("name") var name : String,
    @SerializedName("email") var email : String,
    @SerializedName("role") var role : LoginResponseRole
)

data class LoginResponseRole (
    @SerializedName("id") var id : Int,
    @SerializedName("name") var name : String,
)