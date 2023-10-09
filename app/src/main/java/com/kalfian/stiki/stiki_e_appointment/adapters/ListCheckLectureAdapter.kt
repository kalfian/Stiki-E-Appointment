package com.kalfian.stiki.stiki_e_appointment.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kalfian.stiki.stiki_e_appointment.R
import com.kalfian.stiki.stiki_e_appointment.models.CheckLecture

class ListCheckLectureAdapter : RecyclerView.Adapter<ListCheckLectureAdapter.ViewHolder>() {

    private val itemsCheck: ArrayList<CheckLecture> = ArrayList()
    private var selectedPosition = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_check_lecture, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemsCheck[position]
        holder.checkBox.text = item.text
        holder.checkBox.isChecked = position == selectedPosition

        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            val adapterPosition = holder.adapterPosition
            if (isChecked) {
                selectedPosition = adapterPosition
                notifyDataSetChanged()
                item.isChecked = true
            } else if (adapterPosition == selectedPosition) {
                selectedPosition = RecyclerView.NO_POSITION
                notifyDataSetChanged()
                item.isChecked = false
            }
        }
    }

    override fun getItemCount(): Int {
        return itemsCheck.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val checkBox: RadioButton = view.findViewById(R.id.checkBox)
    }

    fun getList(): List<CheckLecture> {
        return itemsCheck
    }

    fun getCheckedList(): List<CheckLecture> {
        return itemsCheck.filter { it.isChecked }
    }

    fun addList(items: List<CheckLecture>) {
        itemsCheck.addAll(items)
        notifyDataSetChanged()
    }

    fun clear() {
        itemsCheck.clear()
        selectedPosition = RecyclerView.NO_POSITION
        notifyDataSetChanged()
    }
}
