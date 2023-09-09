package com.kalfian.stiki.stiki_e_appointment.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

@kotlinx.serialization.Serializable
data class Activity (

    @SerializedName("id") var id: Int = 0,
    @SerializedName("name") var name: String = "",
    @SerializedName("banner") var banner : String? = null,
    @SerializedName("banner_thumbnail") var bannerThumbnail: String? = null,
    @SerializedName("description") var description: String = "",
    @SerializedName("short_description") var shortDescription: String = "",
    @SerializedName("location") var location: String = "",
    @SerializedName("start_date") var startDate: String = "",
    @SerializedName("end_date") var endDate: String = "",

    @SerializedName("students") var students: ArrayList<Participant> = arrayListOf(),
    @SerializedName("lectures") var lectures: ArrayList<Participant> = arrayListOf(),

): Serializable