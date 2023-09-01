package com.kalfian.stiki.stiki_e_appointment.responses

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse (
    @SerializedName("id") val id : Int = 0,
    @SerializedName("name") val name : String = "",
    @SerializedName("email") val email : String  = "",
    @SerializedName("identity") val identity : String = "",
    @SerializedName("role") val role : RoleResponse = RoleResponse(),
)