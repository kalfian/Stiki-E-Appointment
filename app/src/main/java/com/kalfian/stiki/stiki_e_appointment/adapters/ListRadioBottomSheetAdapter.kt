package com.kalfian.stiki.stiki_e_appointment.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.recyclerview.widget.RecyclerView
import com.kalfian.stiki.stiki_e_appointment.R
import com.kalfian.stiki.stiki_e_appointment.models.CheckRadio

class ListRadioBottomSheetAdapter : RecyclerView.Adapter<ListRadioBottomSheetAdapter.ViewHolder>() {

    private val itemsCheck: ArrayList<CheckRadio> = ArrayList()
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

    fun getList(): List<CheckRadio> {
        return itemsCheck
    }

    fun getChecked(): CheckRadio {
        return itemsCheck[selectedPosition]
    }

    fun addList(items: List<CheckRadio>) {
        itemsCheck.addAll(items)
        notifyDataSetChanged()
    }

    fun clear() {
        itemsCheck.clear()
        selectedPosition = RecyclerView.NO_POSITION
        notifyDataSetChanged()
    }
}
