package com.kalfian.stiki.stiki_e_appointment.requests

import com.google.gson.annotations.SerializedName

data class CreateAppointmentRequest (
    @SerializedName("title") var title : String,
    @SerializedName("date") var date : String,
    @SerializedName("description") var description : String,
    @SerializedName("location") var location : String,
    @SerializedName("lecture_ids") var lectureIds : ArrayList<Int>
)