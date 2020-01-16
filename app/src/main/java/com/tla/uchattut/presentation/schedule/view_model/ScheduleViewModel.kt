package com.tla.uchattut.presentation.schedule.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tla.uchattut.data.db.model.EventDbModel
import com.tla.uchattut.di.schedule.ScheduleComponent
import com.tla.uchattut.domain.schedule.ScheduleInteractor
import com.tla.uchattut.presentation._common.ScopeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class ScheduleViewModel @Inject constructor(
    private val scheduleInteractor: ScheduleInteractor
) : ScopeViewModel(ScheduleComponent::class) {

    private val eventsLiveData = MutableLiveData<List<EventDbModel>>()
    private var newEventDate = Calendar.getInstance()

    private val currentEventDate = Calendar.getInstance()

    fun loadEvents() = viewModelScope.launch(Dispatchers.IO) {
        val formattedDate = formatCalendarDateForDb(currentEventDate)
        val events = scheduleInteractor.getEvents(formattedDate)

        eventsLiveData.postValue(events)
    }

    fun getEventsLiveData(): LiveData<List<EventDbModel>> {
        return eventsLiveData
    }

    fun addEvent(eventDbModel: EventDbModel) = viewModelScope.launch(Dispatchers.IO) {
        scheduleInteractor.addEvent(eventDbModel)
        loadEvents()
    }

    fun setNewEventDate(year: Int, month: Int, dayOfMonth: Int) {
        newEventDate = Calendar.getInstance()
        newEventDate.set(year, month, dayOfMonth)
    }

    fun getNewEventDate(): Calendar {
        return newEventDate
    }

    fun formatCalendarDateForView(calendar: Calendar): String {
        val format = SimpleDateFormat("yyyy.MM.dd")

        return format.format(calendar.time)
    }

    fun formatCalendarDateForDb(calendar: Calendar): String {
        val format = SimpleDateFormat("yyyy.MM.dd")

        return format.format(calendar.time)
    }

    fun loadEvents(year: Int, month: Int, dayOfMonth: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)

            val formattedDate = formatCalendarDateForDb(calendar)
            val events = scheduleInteractor.getEvents(formattedDate)

            eventsLiveData.postValue(events)
        }
    }
}