package com.kalfian.stiki.stiki_e_appointment.utils

import android.content.Context
import android.view.View
import com.google.android.material.snackbar.Snackbar

class SnackbarUtils {
    companion object {
        fun showSnackbar(view: View, message: String, duration: Int = Snackbar.LENGTH_SHORT) {
            Snackbar.make(view, message, duration).show()
        }

        fun showSnacbar(context: Context, message: String, duration: Int = Snackbar.LENGTH_SHORT) =
            Snackbar.make(View(context), message, duration).show()

        fun showSnackbarWithAction(
            view: View,
            message: String,
            actionText: String,
            duration: Int = Snackbar.LENGTH_INDEFINITE,
            actionCallback: () -> Unit
        ) {
            Snackbar.make(view, message, duration)
                .setAction(actionText) { actionCallback() }
                .show()
        }
    }
}
