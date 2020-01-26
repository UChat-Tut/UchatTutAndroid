package com.tla.uchattut.presentation.schedule.adapters

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tla.uchattut.R
import com.tla.uchattut.presentation.schedule.model.EventPresentationModel
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_event_dot.*

class DotEventsAdapter : RecyclerView.Adapter<DotEventsAdapter.DotViewHolder>() {

    private var events = listOf<EventPresentationModel>()
    private var isDotOverflowed = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DotViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_event_dot, parent, false)
        return DotViewHolder(itemView)
    }

    override fun getItemCount(): Int = events.size

    override fun onBindViewHolder(holder: DotViewHolder, position: Int) {
        when {
            position == MAX_DOT_COUNT - 1 && isDotOverflowed -> holder.setPlusIcon()
            position <= MAX_DOT_COUNT - 1 -> holder.bind(events[position])
        }
    }

    class DotViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        fun bind(event: EventPresentationModel) {
            dotView.backgroundTintList = ColorStateList.valueOf(event.color)
        }

        fun setPlusIcon() {
            dotView.background = containerView.resources.getDrawable(R.drawable.ic_plus)
        }
    }

    fun setDots(events: List<EventPresentationModel>) {
        isDotOverflowed = events.size > MAX_DOT_COUNT
        this.events = events.take(MAX_DOT_COUNT)
        notifyDataSetChanged()
    }

    companion object {
        const val MAX_DOT_COUNT = 10
    }
}