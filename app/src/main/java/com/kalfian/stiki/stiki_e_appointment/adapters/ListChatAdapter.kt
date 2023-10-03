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
import com.kalfian.stiki.stiki_e_appointment.utils.Constant
import com.kalfian.stiki.stiki_e_appointment.utils.Helper
import com.kalfian.stiki.stiki_e_appointment.utils.SharedPreferenceUtil
import kotlin.collections.ArrayList

class ListChatAdapter(onClick: AdapterListChatOnClickListener): RecyclerView.Adapter<ListChatAdapter.ViewHolder>() {
    private var list = ArrayList<Chat>()
    private var onClickAdapter = onClick
    private var isLecture = false

    interface AdapterListChatOnClickListener {
        fun onItemClickListener(data: Chat)
    }

    inner class ViewHolder(itemView: View, onClickListener: AdapterListChatOnClickListener): RecyclerView.ViewHolder(itemView) {
        private val b = ListChatBinding.bind(itemView)
        private var clickListener: AdapterListChatOnClickListener = onClickListener

        fun bind(chat: Chat) {
            isLecture = Helper.stringToBoolean(SharedPreferenceUtil.retrieve(itemView.context, Constant.SHARED_IS_LECTURE, "false"))

            b.participantLeft.text = chat.participantName
            b.participantRight.text = chat.participantName

            b.messageLeft.text = chat.message
            b.messageRight.text = chat.message

            b.dateLeft.text = Helper.timestampToDate(chat.timeStamp)
            b.dateRight.text = Helper.timestampToDate(chat.timeStamp)

            itemView.setOnClickListener {
                clickListener.onItemClickListener(list[adapterPosition])
            }

            if (isLecture) {
                if (chat.isLecture) {
                    b.layoutLeft.visibility = View.GONE
                    b.layoutRight.visibility = View.VISIBLE
                } else {
                    b.layoutLeft.visibility = View.VISIBLE
                    b.layoutRight.visibility = View.GONE
                }
            } else {
                if (chat.isLecture) {
                    b.layoutLeft.visibility = View.VISIBLE
                    b.layoutRight.visibility = View.GONE
                } else {
                    b.layoutLeft.visibility = View.GONE
                    b.layoutRight.visibility = View.VISIBLE
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