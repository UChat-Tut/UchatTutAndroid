package com.tla.uchattut.presentation.schedule.calendar_containers

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.DayBinder
import com.tla.uchattut.R
import com.tla.uchattut.presentation.schedule.ScheduleViewModel
import com.tla.uchattut.presentation.schedule.adapters.DotEventsAdapter
import org.threeten.bp.ZonedDateTime

class DayBinder(
    private val viewModel: ScheduleViewModel
) : DayBinder<DayViewContainer> {

    private var adapter: DotEventsAdapter? = null

    override fun create(view: View) = DayViewContainer(view)

    override fun bind(container: DayViewContainer, day: CalendarDay) {
        container.calendarDayTextView.text = day.date.dayOfMonth.toString()
        paintDatCard(container, day)
        provideEvents(container, day)
    }

    private fun paintDatCard(container: DayViewContainer, day: CalendarDay) {
        val resources = container.calendarDayView.resources

        adapter = DotEventsAdapter()
        container.dotEventRecyclerView.adapter = adapter
        container.dotEventRecyclerView.layoutManager = GridLayoutManager(container.dotEventRecyclerView.context, 5)

        when {
            isToday(day) -> {
                container.calendarDayTextView.setTextColor(resources.getColor(R.color.colorAccent))

                if (viewModel.lastSelectedDayView == null) {
                    viewModel.lastSelectedDayView = container.calendarDayView
                }
                viewModel.lastSelectedDayView?.background =
                    resources.getDrawable(R.drawable.bg_day_calendar_selected)

                container.calendarDayView.setOnClickListener {
                    loadEvents(day)
                    setNewEventDate(day)

                    viewModel.lastSelectedDayView?.background = null
                    container.calendarDayView.background =
                        resources.getDrawable(R.drawable.bg_day_calendar_selected)
                    viewModel.lastSelectedDayView = container.calendarDayView
                }
            }
            isCurrentMonth(day) -> {
                container.calendarDayTextView.setTextColor(resources.getColor(android.R.color.black))
                container.calendarDayView.background =
                    resources.getDrawable(R.drawable.bg_day_calendar_in_month)

                viewModel.lastSelectedDayView?.background =
                    resources.getDrawable(R.drawable.bg_day_calendar_selected)

                container.calendarDayView.setOnClickListener {
                    loadEvents(day)
                    setNewEventDate(day)
                    viewModel.lastSelectedDayView?.background = null
                    container.calendarDayView.background =
                        resources.getDrawable(R.drawable.bg_day_calendar_selected)
                    viewModel.lastSelectedDayView = container.calendarDayView
                }
            }
            else -> {
                container.calendarDayTextView.setTextColor(resources.getColor(R.color.silverChariot))
                container.calendarDayView.background =
                    resources.getDrawable(R.drawable.bg_day_calendar_out_month)
            }
        }
    }

    private fun loadEvents(day: CalendarDay) {
        val year = day.date.year
        val month = day.date.monthValue - 1
        val dayOfMonth = day.date.dayOfMonth
        viewModel.loadEvents(year, month, dayOfMonth)
    }

    private fun setNewEventDate(day: CalendarDay) {
        val year = day.date.year
        val month = day.date.monthValue - 1
        val dayOfMonth = day.date.dayOfMonth
        viewModel.setNewEventDate(year, month, dayOfMonth)
    }

    private fun provideEvents(container: DayViewContainer, day: CalendarDay) {
        val dayEvents = viewModel.getEventsForCalendarDay(day)
        if (dayEvents != null) {
            adapter?.setDots(dayEvents)
        }
    }

    private fun isToday(day: CalendarDay): Boolean =
        day.date.isEqual(ZonedDateTime.now().chronology.dateNow())

    private fun isCurrentMonth(day: CalendarDay): Boolean =
        day.owner == DayOwner.THIS_MONTH
}