package com.kalfian.stiki.stiki_e_appointment.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kalfian.stiki.stiki_e_appointment.R
import com.kalfian.stiki.stiki_e_appointment.databinding.ListLogbookBinding
import com.kalfian.stiki.stiki_e_appointment.models.Logbook
import com.kalfian.stiki.stiki_e_appointment.utils.Constant
import com.kalfian.stiki.stiki_e_appointment.utils.Helper
import com.kalfian.stiki.stiki_e_appointment.utils.SharedPreferenceUtil
import kotlin.collections.ArrayList

class ListLogbookAdapter(onClick: AdapterLogbookOnClickListener): RecyclerView.Adapter<ListLogbookAdapter.ViewHolder>() {
    private var list = ArrayList<Logbook>()
    private var onClickAdapter = onClick
    private var isLecture = false

    interface AdapterLogbookOnClickListener {
        fun onItemClickListener(data: Logbook)
        fun onChangeStatusCliclListener(data: Logbook)
    }

    inner class ViewHolder(itemView: View, onClickListener: AdapterLogbookOnClickListener): RecyclerView.ViewHolder(itemView) {
        private val b = ListLogbookBinding.bind(itemView)
        private var clickListener: AdapterLogbookOnClickListener = onClickListener

        fun bind(logbook: Logbook) {
            isLecture = Helper.stringToBoolean(SharedPreferenceUtil.retrieve(itemView.context, Constant.SHARED_IS_LECTURE, "false"))

            itemView.setOnClickListener {
                clickListener.onItemClickListener(list[adapterPosition])
            }

            b.logbookDate.text = logbook.date
            b.logbookDescription.text = logbook.description
            b.logbookProblem.text = logbook.problem ?: "-"
            b.logbookComment.text = logbook.lectureComment ?: "-"

            if (isLecture) {
                b.btnChangeStatusLogbook.visibility = View.VISIBLE
                b.btnChangeStatusLogbook.setOnClickListener {
                    clickListener.onChangeStatusCliclListener(list[adapterPosition])
                }
            } else {
                b.btnChangeStatusLogbook.visibility = View.GONE
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_logbook, parent, false)
        return ViewHolder(v, onClickAdapter)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun addList(items: List<Logbook>) {
        list.addAll(items)
        notifyDataSetChanged()
    }

    fun add(item: Logbook) {
        list.add(item)
        notifyDataSetChanged()
    }

    fun clear() {
        list.clear()
        notifyDataSetChanged()
    }
}