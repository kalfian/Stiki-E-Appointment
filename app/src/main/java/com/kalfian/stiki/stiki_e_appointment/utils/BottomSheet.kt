package com.kalfian.stiki.stiki_e_appointment.utils

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.kalfian.stiki.stiki_e_appointment.adapters.ListBottomSheetButtonAdapter
import com.kalfian.stiki.stiki_e_appointment.databinding.CustomBottomSheetBinding

data class BottomSheetRequest(
    val ctx: Context,
    val title: String,
    val okTitle: String? = null,
    val disableOkButton: Boolean = false,
    val btnOkOnClick: (String) -> Unit = {},
    val recyclerViewAdapter: ListBottomSheetButtonAdapter? = null,
    val useInput: Boolean = false,
    val inputHint: String? = null,
    val inputValue: String? = null
)
fun bottomSheet(params: BottomSheetRequest) : BottomSheetDialog {
    val dialog = BottomSheetDialog(params.ctx, R.style.Theme_Design_Light_BottomSheetDialog)

    // Set the background color with transparency
    val colorResource = ContextCompat.getColor(params.ctx, R.color.background_floating_material_dark)
    val semiTransparentColor = ColorUtils.setAlphaComponent(colorResource, (255 * 0.5f).toInt())
    dialog.window?.setBackgroundDrawable(ColorDrawable(semiTransparentColor))

    // Inflate the custom layout
    // Use View Binding to inflate the custom layout
    val b = CustomBottomSheetBinding.inflate(LayoutInflater.from(params.ctx))
    dialog.setContentView(b.root)

    // Set the RecyclerView adapter when provided
    if (params.recyclerViewAdapter != null) {
        val recyclerView = b.bottomSheetRv
        val lm = LinearLayoutManager(params.ctx, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = lm
        recyclerView.adapter = params.recyclerViewAdapter
    }

    // Set a close button click listener if provided
    b.bottomTitle.text = params.title

    if(params.useInput) {
        b.editText.visibility = View.VISIBLE
        b.editText.hint = params.inputHint
        b.editText.setText(params.inputValue)
    }

    b.bottomCloseBtn.setOnClickListener {
        dialog.dismiss()
    }

    if(params.disableOkButton) {
        b.bottomBtn.visibility = View.GONE
    }

    b.bottomBtn.text = params.okTitle
    b.bottomBtn.setOnClickListener{
        if(params.useInput) {
            params.btnOkOnClick(b.editText.text.toString())
        } else {
            params.btnOkOnClick("")
        }
    }

    dialog.show()
    return dialog
}