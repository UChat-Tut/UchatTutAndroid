package com.tla.uchattut.presentation.schedule.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tla.uchattut.R
import com.tla.uchattut.data.db.model.EventDbModel
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_event.*

class EventsRecyclerAdapter(
    private val eventItemClickListener: OnEventItemClickListener? = null
) : RecyclerView.Adapter<EventsRecyclerAdapter.EventViewHolder>() {

    private val events = mutableListOf<EventDbModel?>(null)

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

    fun addEvents(events: List<EventDbModel>) {
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
            fun bind(eventDbModel: EventDbModel) {
                titleTextView.text = eventDbModel.title
                timeTextView.text = "15:00 - 16:00"

                containerView.setOnClickListener {
                    itemClickListener?.onEventItemClick(eventDbModel.id)
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