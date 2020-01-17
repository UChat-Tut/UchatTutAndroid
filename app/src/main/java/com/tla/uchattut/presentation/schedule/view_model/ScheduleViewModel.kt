package com.tla.uchattut.presentation.schedule.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kizitonwose.calendarview.model.CalendarDay
import com.tla.uchattut.di.schedule.ScheduleComponent
import com.tla.uchattut.domain.schedule.ScheduleInteractor
import com.tla.uchattut.presentation._common.ScopeViewModel
import com.tla.uchattut.presentation._common.SingleLiveEvent
import com.tla.uchattut.presentation.schedule.model.EventPresentationModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class ScheduleViewModel @Inject constructor(
    private val scheduleInteractor: ScheduleInteractor
) : ScopeViewModel(ScheduleComponent::class) {

    private val dayEventsLiveData = MutableLiveData<List<EventPresentationModel>>()
    private val updateCalendarEvent = SingleLiveEvent<Unit>()

    private var dayEvents = HashMap<String, MutableList<EventPresentationModel>>()

    private var newEventDate = Calendar.getInstance()

    private val currentEventDate = Calendar.getInstance()

    fun loadEvents() = viewModelScope.launch(Dispatchers.IO) {
        val events = scheduleInteractor.getEvents(currentEventDate.time)

        dayEventsLiveData.postValue(events)
    }

    fun getEventsLiveData(): LiveData<List<EventPresentationModel>> {
        return dayEventsLiveData
    }

    fun addEvent(eventModel: EventPresentationModel) = viewModelScope.launch(Dispatchers.IO) {
        scheduleInteractor.addEvent(eventModel)
        loadAllPeriodEvents(10)
        loadEvents()
    }

    fun setNewEventDate(year: Int, month: Int, dayOfMonth: Int) {
        newEventDate = Calendar.getInstance()
        newEventDate.set(year, month, dayOfMonth, 0, 0, 0)
    }

    fun getNewEventDate(): Calendar {
        return newEventDate
    }

    fun formatCalendarDate(calendar: Calendar): String {
        val format = SimpleDateFormat("yyyy.MM.dd")
        return format.format(calendar.time)
    }

    fun loadEvents(year: Int, month: Int, dayOfMonth: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth, 0, 0, 0)

            val events = scheduleInteractor.getEvents(calendar.time)

            dayEventsLiveData.postValue(events)
        }
    }

    fun getUpdateCalendarNotifier(): SingleLiveEvent<Unit> {
        return updateCalendarEvent
    }

    fun loadAllPeriodEvents(range: Int) {
        viewModelScope.launch(Dispatchers.IO) {

            val startDayCalendar = Calendar.getInstance()
            startDayCalendar.add(Calendar.MONTH, -range)
            startDayCalendar.set(Calendar.DAY_OF_MONTH, 1)

            val endDayCalendar = Calendar.getInstance()
            endDayCalendar.add(Calendar.MONTH, range + 1)
            endDayCalendar.set(Calendar.DAY_OF_MONTH, 1)

            val events = scheduleInteractor.getEventsInPeriod(startDayCalendar.time, endDayCalendar.time)
            dayEvents = distributeByDate(events)

            updateCalendarEvent.postCall()
        }
    }

    fun dateToString(date: Date): String {
        return scheduleInteractor.dateToString(date)
    }

    fun getEventsForCalendarDay(day: CalendarDay): List<EventPresentationModel>? {
        val year = day.date.year
        val month = day.date.monthValue - 1
        val dayOfMonth = day.date.dayOfMonth

        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        val formattedDate = dateToString(calendar.time)
        return dayEvents[formattedDate]
    }

    private fun distributeByDate(events: List<EventPresentationModel>): HashMap<String, MutableList<EventPresentationModel>> {
        val dateToEventsMap = HashMap<String, MutableList<EventPresentationModel>>()

        events.forEach {
            val dateStr = dateToString(it.date)
            if (dateToEventsMap[dateStr] == null) {
                dateToEventsMap[dateStr] = ArrayList()
            }
            dateToEventsMap[dateStr]?.add(it)
        }
        return dateToEventsMap
    }
}