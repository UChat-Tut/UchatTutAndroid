package com.tla.uchattut.presentation.schedule.calendar_containers

import android.view.View
import com.kizitonwose.calendarview.ui.ViewContainer
import kotlinx.android.synthetic.main.layout_month_header.view.*

class MonthHeaderViewContainer(view: View) : ViewContainer(view) {
    val calendarMonthText = view.calendarMonthText
    val backButton = view.backButton
    val forwardButton = view.forwardButton
}