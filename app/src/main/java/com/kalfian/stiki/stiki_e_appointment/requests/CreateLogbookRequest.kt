package com.kalfian.stiki.stiki_e_appointment.requests

import com.google.gson.annotations.SerializedName

data class CreateLogbookRequest (
    @SerializedName("date") var title : String,
    @SerializedName("description") var startDate : String,
    @SerializedName("problem") var endDate : String?,
    @SerializedName("logbook_proof") var description : String?
)