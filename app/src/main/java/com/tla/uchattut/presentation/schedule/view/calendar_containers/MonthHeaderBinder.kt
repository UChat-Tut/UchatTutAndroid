package com.tla.uchattut.presentation.schedule.view.calendar_containers

import android.annotation.SuppressLint
import android.view.View
import com.kizitonwose.calendarview.CalendarView
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder
import com.tla.uchattut.R

class MonthHeaderBinder(
    private val calendarView: CalendarView
) : MonthHeaderFooterBinder<MonthHeaderViewContainer> {

    private lateinit var months: Array<String>

    override fun create(view: View): MonthHeaderViewContainer =
        MonthHeaderViewContainer(view)

    @SuppressLint("SetTextI18n")
    override fun bind(container: MonthHeaderViewContainer, month: CalendarMonth) {
        val resources = container.view.resources
        months = resources.getStringArray(R.array.months)

        container.calendarMonthText.text = "${months[month.month - 1]}, ${month.year}"

        container.backButton.setOnClickListener {
            calendarView.smoothScrollToMonth(month.yearMonth.minusMonths(1))
        }

        container.forwardButton.setOnClickListener {
            calendarView.smoothScrollToMonth(month.yearMonth.plusMonths(1))
        }
    }
}