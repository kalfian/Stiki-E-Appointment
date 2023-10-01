package com.kalfian.stiki.stiki_e_appointment.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.kalfian.stiki.stiki_e_appointment.R
import com.kalfian.stiki.stiki_e_appointment.databinding.ListActivityBinding
import com.kalfian.stiki.stiki_e_appointment.models.Activity
import com.kalfian.stiki.stiki_e_appointment.models.Participant
import kotlin.collections.ArrayList

class ListActivityAdapter(onClick: AdapterListActivityOnClickListener, isLecture: Boolean): RecyclerView.Adapter<ListActivityAdapter.ViewHolder>() {
    private var list = ArrayList<Activity>()
    private var onClickAdapter = onClick
    private var isLecture = isLecture

    private lateinit var participantAdapter: ListParticipantAdapter

    interface AdapterListActivityOnClickListener {
        fun onItemClickListener(data: Activity)
    }

    inner class ViewHolder(itemView: View, onClickListener: AdapterListActivityOnClickListener): RecyclerView.ViewHolder(itemView), ListParticipantAdapter.AdapterParticipantOnClickListener {
        private val b = ListActivityBinding.bind(itemView)
        private var clickListener: AdapterListActivityOnClickListener = onClickListener

        fun bind(activity: Activity) {
            itemView.setOnClickListener {
                clickListener.onItemClickListener(list[adapterPosition])
            }

            b.activityName.text = activity.name
            b.activityDate.text = "${activity.startDate} - ${activity.endDate}"
            b.activityLocation.text = activity.location
            Glide.with(itemView.context)
                .load(activity.bannerThumbnail)
                .placeholder(CircularProgressDrawable(itemView.context).apply {
                    setColorSchemeColors(
                        ContextCompat.getColor(itemView.context, R.color.dark_blue)
                    )

                    strokeWidth = 2f
                    centerRadius = 10f
                    start()
                })
                .error(R.drawable.noimage)
                .into(b.activityBanner)

        }

        override fun onItemClickListener(data: Participant) {

        }

        override fun onBtnLogbookClickListener(data: Participant) {

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_activity, parent, false)
        return ViewHolder(v, onClickAdapter)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
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