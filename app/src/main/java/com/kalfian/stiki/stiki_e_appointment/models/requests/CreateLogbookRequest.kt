package com.kalfian.stiki.stiki_e_appointment.models.requests

import com.google.gson.annotations.SerializedName

data class CreateLogbookRequest (
    @SerializedName("date") var title : String,
    @SerializedName("description") var description : String,
    @SerializedName("problem") var problem : String?,
    @SerializedName("logbook_proof") var proof : String?
)