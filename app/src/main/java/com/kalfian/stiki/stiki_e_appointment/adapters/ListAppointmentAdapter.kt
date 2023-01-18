package com.kalfian.stiki.stiki_e_appointment.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kalfian.stiki.stiki_e_appointment.R
import com.kalfian.stiki.stiki_e_appointment.databinding.ListActivityBinding
import com.kalfian.stiki.stiki_e_appointment.models.Activity
import kotlin.collections.ArrayList

class ListAppointmentAdapter(onClick: AdapterAppointmentOnClickListener): RecyclerView.Adapter<ListAppointmentAdapter.ViewHolder>() {
    private var list = ArrayList<Activity>()
    private var onClickAdapter = onClick

    interface AdapterAppointmentOnClickListener {
        fun onItemClickListener(data: Activity)
    }

    inner class ViewHolder(itemView: View, onClickListener: AdapterAppointmentOnClickListener): RecyclerView.ViewHolder(itemView) {
        private val b = ListActivityBinding.bind(itemView)
        private var clickListener: AdapterAppointmentOnClickListener = onClickListener

        fun bind(v: Activity) {

            itemView.setOnClickListener {
                clickListener.onItemClickListener(list[adapterPosition])
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_appointment, parent, false)
        return ViewHolder(v, onClickAdapter)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
//        return list.size
        return 8
    }

    fun addList(items: List<Activity>) {
        list.addAll(items)
        notifyDataSetChanged()
    }

    fun add(item: Activity) {
        list.add(item)
        notifyDataSetChanged()
    }

    fun clear() {
        list.clear()
        notifyDataSetChanged()
    }
}