package com.kalfian.stiki.stiki_e_appointment.modules.logbook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.kalfian.stiki.stiki_e_appointment.adapters.ListLogbookAdapter
import com.kalfian.stiki.stiki_e_appointment.databinding.ActivityDetailLogbookStudentBinding
import com.kalfian.stiki.stiki_e_appointment.models.Logbook
import com.kalfian.stiki.stiki_e_appointment.modules.student.CreateLogbookActivity

class DetailLogbookStudentActivity : AppCompatActivity(), ListLogbookAdapter.AdapterLogbookOnClickListener {

    private lateinit var b: ActivityDetailLogbookStudentBinding
    private lateinit var logbookAdapter: ListLogbookAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityDetailLogbookStudentBinding.inflate(layoutInflater)
        val v = b.root
        setContentView(v)

        b.nav.headerTitle.text = "Logbook PT Taspen"
        b.nav.backButton.setOnClickListener {
            finish()
        }

        setupListLogbook()
        getListLogbook()

        b.nav.btnPlus.setOnClickListener {
            val intent = Intent(this, CreateLogbookActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupListLogbook() {
        val lm = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        b.recyclerListLogbook.layoutManager = lm
        logbookAdapter = ListLogbookAdapter(this, false )
        b.recyclerListLogbook.adapter = logbookAdapter
    }

    private fun getListLogbook() {
        logbookAdapter.clear()
        logbookAdapter.addList(listOf(
            Logbook("1", "", ""),
            Logbook("1", "", ""),
            Logbook("1", "", ""),
            Logbook("1", "", ""),
            Logbook("1", "", ""),
            Logbook("1", "", ""),
            Logbook("1", "", ""),
            Logbook("1", "", ""),
        ))
    }

    override fun onItemClickListener(data: Logbook) {

    }

    override fun onChangeStatusCliclListener(data: Logbook) {

    }
}