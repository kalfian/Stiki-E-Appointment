package com.kalfian.stiki.stiki_e_appointment.modules.logbook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.kalfian.stiki.stiki_e_appointment.R
import com.kalfian.stiki.stiki_e_appointment.adapters.ListLogbookAdapter
import com.kalfian.stiki.stiki_e_appointment.databinding.ActivityDetailLogbookLectureBinding
import com.kalfian.stiki.stiki_e_appointment.models.Logbook

class LogbookLectureActivity : AppCompatActivity(), ListLogbookAdapter.AdapterLogbookOnClickListener {

    private lateinit var b: ActivityDetailLogbookLectureBinding
    private lateinit var logbookAdapter: ListLogbookAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityDetailLogbookLectureBinding.inflate(layoutInflater)
        val v = b.root
        setContentView(v)

        b.nav.headerTitle.text = "Logbook PT Taspen"
        b.nav.backButton.setOnClickListener {
            finish()
        }

        setupListLogbook()
        getListLogbook()
    }

    private fun setupListLogbook() {
        val lm = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        b.recyclerListLogbook.layoutManager = lm
        logbookAdapter = ListLogbookAdapter(this )
        b.recyclerListLogbook.adapter = logbookAdapter
    }

    private fun getListLogbook() {
        logbookAdapter.clear()
//        logbookAdapter.addList()
    }

    override fun onItemClickListener(data: Logbook) {

    }

    override fun onChangeStatusCliclListener(data: Logbook) {
        val dialog = BottomSheetDialog(this, com.google.android.material.R.style.Theme_Design_Light_BottomSheetDialog)
        val dialogView = LayoutInflater.from(applicationContext).inflate(
            R.layout.status_bottom_sheet,
            findViewById<LinearLayout>(R.id.status_bottom_sheet)
        )

        dialogView.findViewById<View>(R.id.bottom_close_btn).setOnClickListener {
            dialog.dismiss()
        }

        dialog.setContentView(dialogView)
        dialog.show()
    }
}