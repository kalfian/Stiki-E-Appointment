package com.kalfian.stiki.stiki_e_appointment.utils

import android.content.Context

object Constant {
    const val IS_LECTURE = "is_lecture"

    const val ROLE_LECTURE = "lecture"
    const val ROLE_STUDENT = "student"

    const val SHARED_TOKEN = "shared_token"
    const val SHARED_ROLE = "shared_role"
    const val SHARED_NAME = "shared_name"
    const val SHARED_IS_LECTURE = "shared_is_lecture"
    const val SHARED_IDENTITY = "shared_identity"
    const val SHARED_ID = "shared_id"

    const val DETAIL_ACTIVITY_ID = "activity_id"

    const val DETAIL_APPOINTMENT_ID = "appointment_id"
    const val DETAIL_LOGBOOK_ID = "logbook_id"
    const val DETAIL_PARTICIPANT_ID = "participant_id"
    const val DETAIL_PARTICIPANT_NAME = "participant_name"
    const val DETAIL_PARTICIPANT_IDENTITY = "participant_identity"

    const val INTENT_CHAT_TITLE = "com.kalfian.stiki.stiki_e_appointment.CHAT_TITLE"
    const val INTENT_APPOINTMENT_ID = "com.kalfian.stiki.stiki_e_appointment.APPOINTMENT_ID"

    const val INTENT_GO_TO_NOTIFICATION = "com.kalfian.stiki.stiki_e_appointment.GO_TO_NOTIFICATION"

    const val STATUS_APPOINTMENT_PENDING = 200
    const val STATUS_APPOINTMENT_ACCEPTED = 201
    const val STATUS_APPOINTMENT_REJECTED = 202
    const val STATUS_APPOINTMENT_CANCELED = 203
    const val STATUS_APPOINTMENT_DONE = 204


    const val notification_channel_id = "com.kalfian.stiki-e-appointment.default_channel_id"
    const val notification_channel_name = "com.kalfian.stiki.stiki_e_appointment.notification_channel_name"
}