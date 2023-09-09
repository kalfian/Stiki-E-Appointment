package com.kalfian.stiki.stiki_e_appointment.utils

import android.app.Activity
import androidx.core.content.res.ResourcesCompat
import com.kalfian.stiki.stiki_e_appointment.R
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle

object Alert {
    fun showError(
        activity: Activity,
        message: String,
        description: String,
        gravity: Int = MotionToast.GRAVITY_BOTTOM,
        duration: Long = MotionToast.LONG_DURATION,
        font: android.graphics.Typeface? = ResourcesCompat.getFont(activity, R.font.ubuntu_regular)
    ) {
        MotionToast.createColorToast(
            activity,
            message,
            description,
            MotionToastStyle.ERROR,
            gravity,
            duration,
            font
        )
    }
}
