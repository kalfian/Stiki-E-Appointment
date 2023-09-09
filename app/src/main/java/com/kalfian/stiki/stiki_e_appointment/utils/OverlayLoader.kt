package com.kalfian.stiki.stiki_e_appointment.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.kalfian.stiki.stiki_e_appointment.R

class OverlayLoader(private val context: Context) {

    private var overlayView: View? = null

    fun show() {
        val inflater = LayoutInflater.from(context)
        overlayView = inflater.inflate(R.layout.overlay_loader, null)

        val rootView = (context as? AppCompatActivity)?.findViewById<ViewGroup>(android.R.id.content)
        rootView?.addView(overlayView)
    }

    fun hide() {
        overlayView?.let { view ->
            val rootView = (context as? AppCompatActivity)?.findViewById<ViewGroup>(android.R.id.content)
            rootView?.removeView(view)
        }
        overlayView = null
    }
}