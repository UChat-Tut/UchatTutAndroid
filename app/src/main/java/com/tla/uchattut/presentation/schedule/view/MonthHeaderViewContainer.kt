package com.tla.uchattut.presentation.schedule.view

import android.view.View
import com.kizitonwose.calendarview.ui.ViewContainer
import kotlinx.android.synthetic.main.layout_calendar_day.view.*
import kotlinx.android.synthetic.main.layout_month_header.view.*

class MonthHeaderViewContainer(view: View) : ViewContainer(view) {
    val calendarMonthText = view.calendarMonthText
    val backButton = view.backButton
    val forwardButton = view.forwardButton
}