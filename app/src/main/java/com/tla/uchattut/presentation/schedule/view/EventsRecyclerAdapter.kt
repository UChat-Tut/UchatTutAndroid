package com.tla.uchattut.presentation.schedule.view

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

    private val events = mutableListOf<EventPresentationModel?>(null)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        when (viewType) {
            EventViewType.CONTENT.value -> {
                val view = inflater.inflate(R.layout.item_event, parent, false)
                return EventViewHolder.ContentEventViewHolder(view, eventItemClickListener)
            }
            EventViewType.ADD_EVENT.value -> {
                val view = inflater.inflate(R.layout.item_add_event, parent, false)
                return EventViewHolder.AddEventViewHolder(view, eventItemClickListener)
            }
            else -> {
                throw IllegalStateException("No such viewType: $viewType")
            }
        }
    }

    override fun getItemCount(): Int = events.size

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        if (holder is EventViewHolder.ContentEventViewHolder) {
            holder.bind(events[position]!!)
        }
    }

    fun addEvents(events: List<EventPresentationModel>) {
        this.events.clear()
        this.events.addAll(events)
        this.events.add(null)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if (events[position] == null) {
            EventViewType.ADD_EVENT.value
        } else {
            EventViewType.CONTENT.value
        }
    }

    sealed class EventViewHolder(
        override val containerView: View
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {


        class ContentEventViewHolder(
            view: View,
            private val itemClickListener: OnEventItemClickListener?
        ) : EventViewHolder(view) {
            @SuppressLint("SetTextI18n")
            fun bind(eventModel: EventPresentationModel) {
                titleTextView.text = eventModel.title

                val startHour = eventModel.startCalendarTime.get(Calendar.HOUR_OF_DAY)
                val startMinute = eventModel.startCalendarTime.get(Calendar.MINUTE)

                val endHour = eventModel.endCalendarTime.get(Calendar.HOUR_OF_DAY)
                val endMinute = eventModel.startCalendarTime.get(Calendar.MINUTE)

                timeTextView.text = "${String.format("%02d", startHour)}:${String.format("%02d", startMinute)} - " +
                        "${String.format("%02d", endHour)}:${String.format("%02d", endMinute)}"

                containerView.setOnClickListener {
                    itemClickListener?.onEventItemClick(eventModel.id)
                }
            }
        }

        class AddEventViewHolder(
            view: View,
            private val itemClickListener: OnEventItemClickListener?
        ) : EventViewHolder(view) {
            init {
                containerView.setOnClickListener {
                    itemClickListener?.onNewItemClick()
                }
            }
        }
    }

    enum class EventViewType(val value: Int) {
        CONTENT(0),
        ADD_EVENT(1)
    }

    interface OnEventItemClickListener {

        fun onNewItemClick()

        fun onEventItemClick(id: Int)

    }
}