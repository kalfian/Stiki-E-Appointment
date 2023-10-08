package com.kalfian.stiki.stiki_e_appointment.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

@kotlinx.serialization.Serializable
data class Logbook (
    @SerializedName("id") var id: Int = 0,
    @SerializedName("appointment_id") var appointmentId: Int = 0,
    @SerializedName("student_id") var studentId: Int = 0,
    @SerializedName("date") var date: String = "",
    @SerializedName("date_db") var dateDB: String = "",
    @SerializedName("description") var description: String = "",
    @SerializedName("problem") var problem: String? = null,
    @SerializedName("logbook_proof") var logbookProof: String? = null,
    @SerializedName("lecture_comment") var lectureComment: String? = null,
): Serializable