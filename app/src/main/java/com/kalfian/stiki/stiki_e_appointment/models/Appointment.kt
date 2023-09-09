package com.kalfian.stiki.stiki_e_appointment.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Appointment (
    var id: String = "",
    var image: String = "",
    var title: String = "",
    var startDate: String = "",
    var endDate: String = "",

    @SerializedName("student") var student: Participant?,
    @SerializedName("lecture") var lecture: Participant?,
    @SerializedName("lecture2") var lecture2: Participant?,
): Serializable