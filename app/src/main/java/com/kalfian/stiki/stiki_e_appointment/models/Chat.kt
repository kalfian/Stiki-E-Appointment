package com.kalfian.stiki.stiki_e_appointment.models

import com.google.gson.annotations.SerializedName

@kotlinx.serialization.Serializable
data class Chat(
    @SerializedName("id") var id: String = "",
    @SerializedName("participant_id") var participantId: Int = 0,
    @SerializedName("participant_name") var participantName: String = "",
    @SerializedName("timestamp") var timeStamp: Long = 0,
    @SerializedName("message") var message: String = "",
    @SerializedName("is_lecture") var isLecture: Boolean = false
)