package com.kalfian.stiki.stiki_e_appointment.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

@kotlinx.serialization.Serializable
data class BottomSheetButton (
    @SerializedName("id") var id: Int = 0,
    @SerializedName("title") var title: String = "",
    @SerializedName("payload") var payload: String = ""
): Serializable