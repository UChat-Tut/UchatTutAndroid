package com.tla.uchattut.domain.schedule

import com.tla.uchattut.data.db.model.EventDbModel
import com.tla.uchattut.data.repositories.events.EventsRepositoryImpl
import com.tla.uchattut.presentation.schedule.model.EventPresentationModel
import java.util.*
import javax.inject.Inject

class ScheduleInteractor @Inject constructor(
    private val eventsRepository: EventsRepositoryImpl
) {
    suspend fun addEvent(eventModel: EventPresentationModel) {
        eventsRepository.addEvent(eventModel)
    }

    suspend fun removeEvent(eventDbModel: EventDbModel) {
        eventsRepository.removeEvent(eventDbModel)
    }

    suspend fun getEvents(): List<EventDbModel> {
        return eventsRepository.getEvents()
    }

    suspend fun getEvents(date: Date): List<EventPresentationModel> {
        return eventsRepository.getEvents(date)
    }

    suspend fun getEventsInPeriod(start: Date, end: Date): List<EventPresentationModel> {
        return eventsRepository.getEventsInPeriod(start, end)
    }

    fun dateToString(date: Date): String {
        return eventsRepository.dateToString(date)
    }
}