package com.tla.uchattut.presentation.schedule.calendar_containers

import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.kizitonwose.calendarview.ui.ViewContainer
import kotlinx.android.synthetic.main.layout_calendar_day.view.*

class DayViewContainer(view: View) : ViewContainer(view) {
    val calendarDayTextView: TextView = view.calendarDayTextView
    val calendarDayView: LinearLayout = view.calendarDayView
    val lineViews = arrayOf(
        view.lineView1,
        view.lineView2,
        view.lineView3
    )
}