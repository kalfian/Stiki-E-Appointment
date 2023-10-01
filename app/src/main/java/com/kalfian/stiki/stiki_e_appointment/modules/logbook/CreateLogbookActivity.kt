package com.kalfian.stiki.stiki_e_appointment.modules.logbook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import androidx.core.content.res.ResourcesCompat
import com.kalfian.stiki.stiki_e_appointment.R
import com.kalfian.stiki.stiki_e_appointment.databinding.ActivityCreateLogbookBinding
import com.kalfian.stiki.stiki_e_appointment.utils.OverlayLoader
import setupDateTimePicker

class CreateLogbookActivity : AppCompatActivity() {

    private lateinit var b: ActivityCreateLogbookBinding
    private lateinit var overlayLoader: OverlayLoader

    private var requestActivityId: Int = 0
    private var requestDate: String = ""
    private var requestDescription: String = ""
    private var requestProblem: String = ""
    private var requestProof: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        b = ActivityCreateLogbookBinding.inflate(layoutInflater)
        val v = b.root
        overlayLoader = OverlayLoader(this)
        setContentView(v)

        setupLayout()

        b.btnCreateLogbook.setOnClickListener {

        }
    }

    private fun setupLayout() {
        b.nav.headerTitle.text = "Tambah Logbook"
        b.nav.backButton.setOnClickListener {
            finish()
        }

        b.logbookDate.setupDateTimePicker(this) { selected, selectedDB ->
            b.logbookDate.setText(selected)
        }

        b.logbookDescription.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE || (event != null && event.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_ENTER)) {
                // Insert a newline character when the Enter key is pressed
                b.logbookDescription.text?.insert(b.logbookDescription.selectionStart, "\n")
                return@setOnEditorActionListener true
            }
            false
        }

        b.logbookProblem.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE || (event != null && event.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_ENTER)) {
                // Insert a newline character when the Enter key is pressed
                b.logbookProblem.text?.insert(b.logbookProblem.selectionStart, "\n")
                return@setOnEditorActionListener true
            }
            false
        }

        b.logbookProof.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE || (event != null && event.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_ENTER)) {
                // Insert a newline character when the Enter key is pressed
                b.logbookProof.text?.insert(b.logbookProof.selectionStart, "\n")
                return@setOnEditorActionListener true
            }
            false
        }
    }
}