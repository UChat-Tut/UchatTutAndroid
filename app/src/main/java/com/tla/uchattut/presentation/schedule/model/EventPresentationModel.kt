package com.tla.uchattut.presentation.schedule.model

import java.util.*

data class EventPresentationModel(
    val id: Int = 0,
    val title: String,
    val date: Date,
    val startCalendarTime: Calendar,
    val endCalendarTime: Calendar
)