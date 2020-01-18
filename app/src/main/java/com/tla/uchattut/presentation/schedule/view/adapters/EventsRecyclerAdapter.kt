package com.tla.uchattut.presentation.schedule.view.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tla.uchattut.R
import com.tla.uchattut.presentation.schedule.model.EventPresentationModel
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_event.*
import java.util.*

class EventsRecyclerAdapter(
    private val eventItemClickListener: OnEventItemClickListener? = null
) : RecyclerView.Adapter<EventsRecyclerAdapter.EventViewHolder>() {

    private var events = listOf<EventPresentationModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_event, parent, false)
        return EventViewHolder(
            view,
            eventItemClickListener
        )
    }

    override fun getItemCount(): Int = events.size

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(events[position])
    }

    fun addEvents(events: List<EventPresentationModel>) {
        this.events = events
        notifyDataSetChanged()
    }

    class EventViewHolder(
        override val containerView: View,
        private val itemClickListener: OnEventItemClickListener?
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        @SuppressLint("SetTextI18n")
        fun bind(eventModel: EventPresentationModel) {
            titleTextView.text = eventModel.title

            val startHour = eventModel.startCalendarTime.get(Calendar.HOUR_OF_DAY)
            val startMinute = eventModel.startCalendarTime.get(Calendar.MINUTE)

            val endHour = eventModel.endCalendarTime.get(Calendar.HOUR_OF_DAY)
            val endMinute = eventModel.startCalendarTime.get(Calendar.MINUTE)

            timeTextView.text =
                "${String.format("%02d", startHour)}:${String.format("%02d", startMinute)} - " +
                        "${String.format("%02d", endHour)}:${String.format("%02d", endMinute)}"

            containerView.setOnClickListener {
                itemClickListener?.onEventItemClick(eventModel.id)
            }
        }
    }

    interface OnEventItemClickListener {
        fun onEventItemClick(id: Int)
    }
}