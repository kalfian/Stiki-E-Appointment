package com.kalfian.stiki.stiki_e_appointment.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kalfian.stiki.stiki_e_appointment.R
import com.kalfian.stiki.stiki_e_appointment.databinding.ListBottomSheetButtonBinding
import com.kalfian.stiki.stiki_e_appointment.models.BottomSheetButton

class ListBottomSheetButtonAdapter(onClick: AdapterBottomSheetButton): RecyclerView.Adapter<ListBottomSheetButtonAdapter.ViewHolder>() {
    private var list = ArrayList<BottomSheetButton>()
    private var onClickAdapter = onClick

    interface AdapterBottomSheetButton {
        fun onItemClickListener(data: BottomSheetButton)
    }

    inner class ViewHolder(itemView: View, onClickListener: AdapterBottomSheetButton): RecyclerView.ViewHolder(itemView) {
        private val b = ListBottomSheetButtonBinding.bind(itemView)
        private var clickListener: AdapterBottomSheetButton = onClickListener

        fun bind(v: BottomSheetButton) {

            b.bottomBtn.text = v.title
            b.bottomBtn.setOnClickListener{
                clickListener.onItemClickListener(list[adapterPosition])
            }

            itemView.setOnClickListener {
                clickListener.onItemClickListener(list[adapterPosition])
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_bottom_sheet_button, parent, false)
        return ViewHolder(v, onClickAdapter)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun addList(items: List<BottomSheetButton>) {
        list.addAll(items)
        notifyDataSetChanged()
    }

    fun add(item: BottomSheetButton) {
        list.add(item)
        notifyDataSetChanged()
    }

    fun clear() {
        list.clear()
        notifyDataSetChanged()
    }
}