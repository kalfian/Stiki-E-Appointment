package com.kalfian.stiki.stiki_e_appointment.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kalfian.stiki.stiki_e_appointment.R
import com.kalfian.stiki.stiki_e_appointment.databinding.ListAppointmentBinding
import com.kalfian.stiki.stiki_e_appointment.models.Appointment
import kotlin.collections.ArrayList

class ListAppointmentAdapter(onClick: AdapterAppointmentOnClickListener): RecyclerView.Adapter<ListAppointmentAdapter.ViewHolder>() {
    private var list = ArrayList<Appointment>()
    private var onClickAdapter = onClick

    interface AdapterAppointmentOnClickListener {
        fun onItemClickListener(data: Appointment)
    }

    inner class ViewHolder(itemView: View, onClickListener: AdapterAppointmentOnClickListener): RecyclerView.ViewHolder(itemView) {
        private val b = ListAppointmentBinding.bind(itemView)
        private var clickListener: AdapterAppointmentOnClickListener = onClickListener

        fun bind(v: Appointment) {

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
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun addList(items: List<Appointment>) {
        list.addAll(items)
        notifyDataSetChanged()
    }

    fun add(item: Appointment) {
        list.add(item)
        notifyDataSetChanged()
    }

    fun clear() {
        list.clear()
        notifyDataSetChanged()
    }
}