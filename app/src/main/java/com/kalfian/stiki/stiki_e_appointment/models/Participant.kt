package com.kalfian.stiki.stiki_e_appointment.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable
@kotlinx.serialization.Serializable
data class Participant (
    @SerializedName("id") var id: String = "",
    @SerializedName("is_lecturer") var isLecturer: Int = 0,

    @SerializedName("user") var user: User = User(),

): Serializable