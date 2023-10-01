package com.kalfian.stiki.stiki_e_appointment.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable
@kotlinx.serialization.Serializable
data class Appointment (
    @SerializedName("id") var id: Int = 0,
    @SerializedName("title") var title: String = "",
    @SerializedName("location") var location: String = "",
    @SerializedName("status") var status: Int = 200,
    @SerializedName("status_text") var statusText: String = "",
    @SerializedName("start_date") var startDate: String = "",
    @SerializedName("end_date") var endDate: String = "",
    @SerializedName("description") var description: String = "",
    @SerializedName("student") var student: User?,
    @SerializedName("lecture") var lecture: User?,
    @SerializedName("lecture2") var lecture2: User?,
): Serializable