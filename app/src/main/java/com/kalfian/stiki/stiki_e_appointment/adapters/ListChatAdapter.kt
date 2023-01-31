package com.kalfian.stiki.stiki_e_appointment.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kalfian.stiki.stiki_e_appointment.R
import com.kalfian.stiki.stiki_e_appointment.databinding.ListActivityBinding
import com.kalfian.stiki.stiki_e_appointment.databinding.ListChatBinding
import com.kalfian.stiki.stiki_e_appointment.models.Activity
import com.kalfian.stiki.stiki_e_appointment.models.Chat
import com.kalfian.stiki.stiki_e_appointment.models.Participant
import kotlin.collections.ArrayList

class ListChatAdapter(onClick: AdapterListChatOnClickListener, isLecture: Boolean): RecyclerView.Adapter<ListChatAdapter.ViewHolder>() {
    private var list = ArrayList<Chat>()
    private var onClickAdapter = onClick
    private var isLecture = isLecture

    interface AdapterListChatOnClickListener {
        fun onItemClickListener(data: Chat)
    }

    inner class ViewHolder(itemView: View, onClickListener: AdapterListChatOnClickListener): RecyclerView.ViewHolder(itemView) {
        private val b = ListChatBinding.bind(itemView)
        private var clickListener: AdapterListChatOnClickListener = onClickListener

        fun bind(chat: Chat) {
            itemView.setOnClickListener {
                clickListener.onItemClickListener(list[adapterPosition])
            }

            if (isLecture) {
                if (chat.isLecture) {
                    b.layoutLeft.visibility = View.GONE
                    b.layoutRight.visibility = View.VISIBLE
                    b.participantRight.text = chat.title
                } else {
                    b.layoutLeft.visibility = View.VISIBLE
                    b.layoutRight.visibility = View.GONE
                    b.participantLeft.text = chat.title
                }
            } else {
                if (chat.isLecture) {
                    b.layoutLeft.visibility = View.VISIBLE
                    b.layoutRight.visibility = View.GONE
                    b.participantLeft.text = chat.title
                } else {
                    b.layoutLeft.visibility = View.GONE
                    b.layoutRight.visibility = View.VISIBLE
                    b.participantRight.text = chat.title
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_chat, parent, false)
        return ViewHolder(v, onClickAdapter)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun addList(items: List<Chat>) {
        list.addAll(items)
        notifyDataSetChanged()
    }

    fun add(item: Chat) {
        list.add(item)
        notifyDataSetChanged()
    }

    fun clear() {
        list.clear()
        notifyDataSetChanged()
    }
}