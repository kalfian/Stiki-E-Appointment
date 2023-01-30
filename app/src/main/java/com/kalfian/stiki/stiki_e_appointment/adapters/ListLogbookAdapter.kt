package com.kalfian.stiki.stiki_e_appointment.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kalfian.stiki.stiki_e_appointment.R
import com.kalfian.stiki.stiki_e_appointment.databinding.ListLogbookBinding
import com.kalfian.stiki.stiki_e_appointment.models.Logbook
import kotlin.collections.ArrayList

class ListLogbookAdapter(onClick: AdapterLogbookOnClickListener, isLecture: Boolean): RecyclerView.Adapter<ListLogbookAdapter.ViewHolder>() {
    private var list = ArrayList<Logbook>()
    private var onClickAdapter = onClick
    private var isLecture = isLecture

    interface AdapterLogbookOnClickListener {
        fun onItemClickListener(data: Logbook)
        fun onChangeStatusCliclListener(data: Logbook)
    }

    inner class ViewHolder(itemView: View, onClickListener: AdapterLogbookOnClickListener): RecyclerView.ViewHolder(itemView) {
        private val b = ListLogbookBinding.bind(itemView)
        private var clickListener: AdapterLogbookOnClickListener = onClickListener

        fun bind(logbook: Logbook) {

            itemView.setOnClickListener {
                clickListener.onItemClickListener(list[adapterPosition])
            }

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