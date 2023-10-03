package com.kalfian.stiki.stiki_e_appointment.utils

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import com.google.android.material.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.kalfian.stiki.stiki_e_appointment.databinding.CustomBottomSheetBinding

data class BottomSheetRequest(
        val ctx: Context,
        val title: String,
        val okTitle: String? = null,
        val disableOkButton: Boolean = false,
        val btnOkOnClick: View.OnClickListener? = null,
)
fun bottomSheet(params: BottomSheetRequest) {
        val dialog = BottomSheetDialog(params.ctx, R.style.Theme_Design_Light_BottomSheetDialog)

        // Set the background color with transparency
        val colorResource = ContextCompat.getColor(params.ctx, R.color.background_floating_material_dark)
        val semiTransparentColor = ColorUtils.setAlphaComponent(colorResource, (255 * 0.5f).toInt())
        dialog.window?.setBackgroundDrawable(ColorDrawable(semiTransparentColor))

        // Inflate the custom layout
        // Use View Binding to inflate the custom layout
        val b = CustomBottomSheetBinding.inflate(LayoutInflater.from(params.ctx))
        dialog.setContentView(b.root)

        // Set a close button click listener if provided
        b.bottomTitle.text = params.title

        b.bottomCloseBtn.setOnClickListener {
            dialog.dismiss()
        }

        b.bottomBtn.text = params.okTitle
        b.bottomBtn.setOnClickListener(params.btnOkOnClick)

        dialog.show()
}