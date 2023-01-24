package com.kalfian.stiki.stiki_e_appointment.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kalfian.stiki.stiki_e_appointment.R
import com.kalfian.stiki.stiki_e_appointment.databinding.ListAppointmentBinding
import com.kalfian.stiki.stiki_e_appointment.databinding.ListParticipantBinding
import com.kalfian.stiki.stiki_e_appointment.models.Appointment
import com.kalfian.stiki.stiki_e_appointment.models.Participant
import kotlin.collections.ArrayList

class ListParticipantAdapter(onClick: AdapterParticipantOnClickListener, isLecture: Boolean): RecyclerView.Adapter<ListParticipantAdapter.ViewHolder>() {
    private var list = ArrayList<Participant>()
    private var onClickAdapter = onClick

    interface AdapterParticipantOnClickListener {
        fun onItemClickListener(data: Participant)
    }

    inner class ViewHolder(itemView: View, onClickListener: AdapterParticipantOnClickListener): RecyclerView.ViewHolder(itemView) {
        private val b = ListParticipantBinding.bind(itemView)
        private var clickListener: AdapterParticipantOnClickListener = onClickListener

        fun bind(v: Participant) {

            itemView.setOnClickListener {
                clickListener.onItemClickListener(list[adapterPosition])
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_participant, parent, false)
        return ViewHolder(v, onClickAdapter)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun addList(items: List<Participant>) {
        list.addAll(items)
        notifyDataSetChanged()
    }

    fun add(item: Participant) {
        list.add(item)
        notifyDataSetChanged()
    }

    fun clear() {
        list.clear()
        notifyDataSetChanged()
    }
}