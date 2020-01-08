package com.tla.uchattut.presentation.schedule.view

import android.view.View
import com.kizitonwose.calendarview.ui.ViewContainer
import kotlinx.android.synthetic.main.layout_calendar_day.view.*

class DayViewContainer(view: View) : ViewContainer(view) {
    val calendarDayTextView = view.calendarDayTextView
    val calendarDayView = view.calendarDayView
}