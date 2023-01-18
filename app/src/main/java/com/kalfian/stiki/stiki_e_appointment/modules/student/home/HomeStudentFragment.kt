package com.kalfian.stiki.stiki_e_appointment.modules.student.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.kalfian.stiki.stiki_e_appointment.R
import com.kalfian.stiki.stiki_e_appointment.adapters.ListActivityAdapter
import com.kalfian.stiki.stiki_e_appointment.databinding.FragmentHomeStudentBinding


class HomeStudentFragment : Fragment(R.layout.fragment_home_student), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var b: FragmentHomeStudentBinding
    private lateinit var activityAdapter: ListActivityAdapter
    private lateinit var activityLayoutManager: ListLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_student, container, false)


    }

    override fun onRefresh() {
        TODO("Not yet implemented")
    }
}