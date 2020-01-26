package com.tla.uchattut.presentation.schedule

import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kizitonwose.calendarview.model.CalendarDay
import com.tla.uchattut.R
import com.tla.uchattut.data.repositories.events.EventsRepositoryImpl
import com.tla.uchattut.domain._common.CalendarWrapper
import com.tla.uchattut.domain.schedule.ScheduleInteractor
import com.tla.uchattut.presentation._common.SingleLiveEvent
import com.tla.uchattut.presentation._common.resources.ResourceManager
import com.tla.uchattut.presentation.schedule.model.EventPresentationModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class ScheduleViewModel @Inject constructor(
    private val resources: ResourceManager
) : ViewModel() {

    private val scheduleInteractor = ScheduleInteractor(EventsRepositoryImpl())

    private val dayEventsLiveData = MutableLiveData<List<EventPresentationModel>>()
    private val updateCalendarEvent = SingleLiveEvent<Unit>()
    private val dateTextLiveData = MutableLiveData<String>()
    private val startTimeLiveData = MutableLiveData<String>()
    private val endTimeLiveData = MutableLiveData<String>()
    private val eventColorLiveData = MutableLiveData<Int>()

    private var dayEvents = HashMap<String, MutableList<EventPresentationModel>>()

    private var chosenCalendarDay = CalendarWrapper.getDefaultInstance()
    private var chosenStartTime = CalendarWrapper.getDefaultInstance()
    private var chosenEndTime = CalendarWrapper.getDefaultInstance()
    private var chosenColor = Color.BLUE

    private val currentEventDate = CalendarWrapper.getDefaultInstance()

    fun loadEvents() = viewModelScope.launch(Dispatchers.IO) {
        val events = scheduleInteractor.getEvents(currentEventDate.time)

        dayEventsLiveData.postValue(events)
    }

    fun getEventsLiveData(): LiveData<List<EventPresentationModel>> {
        return dayEventsLiveData
    }

    fun addEvent(eventModel: EventPresentationModel) = viewModelScope.launch(Dispatchers.IO) {
        scheduleInteractor.addEvent(eventModel)

        addEventToDayEventMap(eventModel)
        updateCalendarEvent.postCall()

        val events = dayEventsLiveData.value?.toMutableList() ?: mutableListOf()
        events.add(eventModel)
        dayEventsLiveData.postValue(events)
    }

    private fun addEventToDayEventMap(event: EventPresentationModel) {
        val dateStr = dateToString(event.date)
        if (dayEvents[dateStr] == null) {
            dayEvents[dateStr] = ArrayList()
        }
        dayEvents[dateStr]?.add(event)
    }

    fun setNewEventDate(year: Int, month: Int, dayOfMonth: Int) {
        chosenCalendarDay.set(year, month, dayOfMonth, 0, 0, 0)
        dateTextLiveData.postValue(formatCalendarDate(chosenCalendarDay))
    }

    fun getChosenCalendarDay(): Calendar {
        return chosenCalendarDay
    }

    fun getChosenStartCalendarTime(): Calendar {
        return chosenStartTime
    }

    fun getChosenEndCalendarTime(): Calendar {
        return chosenEndTime
    }

    fun getStartTimeLiveData(): LiveData<String> {
        return startTimeLiveData
    }

    fun getEndTimeLiveData(): LiveData<String> {
        return endTimeLiveData
    }

    private fun formatCalendarDate(calendar: Calendar): String {
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        Calendar.SUNDAY
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)
        return "${resources.getStringArray(R.array.short_days_of_week)[dayOfWeek - 1]}, $dayOfMonth " +
                " ${resources.getStringArray(R.array.short_months)[month]}. $year г."
    }

    fun loadEvents(year: Int, month: Int, dayOfMonth: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val calendar = CalendarWrapper.getDefaultInstance()
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

            val events =
                scheduleInteractor.getEventsInPeriod(startDayCalendar.time, endDayCalendar.time)
            dayEvents = distributeByDate(events)

            updateCalendarEvent.postCall()
        }
    }

    private fun dateToString(date: Date): String {
        return scheduleInteractor.dateToString(date)
    }

    fun getDateTextLiveData(): LiveData<String> {
        return dateTextLiveData
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

    private fun Calendar.roundTime(minutes: Int) {
        val currentMinutes = get(Calendar.MINUTE)

        val mod = currentMinutes % minutes
        add(Calendar.MINUTE, minutes - mod)
    }

    private fun Calendar.stringifyTime(): String {
        val hours = get(Calendar.HOUR_OF_DAY)
        val minutes = get(Calendar.MINUTE)
        return "${String.format("%02d", hours)}:${String.format("%02d", minutes)}"
    }

    fun initBottomSheetData() {
        val calendar = Calendar.getInstance()
        chosenCalendarDay.set(Calendar.YEAR, calendar.get(Calendar.YEAR))
        chosenCalendarDay.set(Calendar.MONTH, calendar.get(Calendar.MONTH))
        chosenCalendarDay.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH))
        dateTextLiveData.postValue(formatCalendarDate(chosenCalendarDay))

        val startCalendar = Calendar.getInstance()
        startCalendar.roundTime(30)
        chosenStartTime.set(Calendar.HOUR_OF_DAY, startCalendar.get(Calendar.HOUR_OF_DAY))
        chosenStartTime.set(Calendar.MINUTE, startCalendar.get(Calendar.MINUTE))

        val endCalendar = Calendar.getInstance()
        endCalendar.add(Calendar.HOUR_OF_DAY, 1)
        endCalendar.roundTime(30)
        chosenEndTime.set(Calendar.HOUR_OF_DAY, endCalendar.get(Calendar.HOUR_OF_DAY))
        chosenEndTime.set(Calendar.MINUTE, endCalendar.get(Calendar.MINUTE))

        val startTime = startCalendar.stringifyTime()
        startTimeLiveData.postValue(startTime)

        val endTime = endCalendar.stringifyTime()
        endTimeLiveData.postValue(endTime)
    }

    fun updateStartTime(hours: Int, minutes: Int) {
        chosenStartTime.set(Calendar.HOUR_OF_DAY, hours)
        chosenStartTime.set(Calendar.MINUTE, minutes)

        val startTime = chosenStartTime.stringifyTime()
        startTimeLiveData.postValue(startTime)
    }

    fun updateEndTime(hours: Int, minutes: Int) {
        chosenEndTime.set(Calendar.HOUR_OF_DAY, hours)
        chosenEndTime.set(Calendar.MINUTE, minutes)

        val endTime = chosenEndTime.stringifyTime()
        endTimeLiveData.postValue(endTime)
    }

    fun updateEventColor(color: Int) {
        chosenColor = color
        eventColorLiveData.postValue(chosenColor)
    }

    fun getChosenColor(): Int {
        return chosenColor
    }

    fun getEventColorLiveData(): LiveData<Int> {
        return eventColorLiveData
    }
}