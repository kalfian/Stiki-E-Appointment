package com.kalfian.stiki.stiki_e_appointment.models.requests

import com.google.gson.annotations.SerializedName

data class UpdateLogbookLectureRequest (
    @SerializedName("lecture_comment") var comment : String,
)