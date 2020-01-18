package com.tla.uchattut.domain._common

import java.util.*

object CalendarWrapper {
    fun getDefaultInstance(): Calendar {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = 0
        return calendar
    }
}