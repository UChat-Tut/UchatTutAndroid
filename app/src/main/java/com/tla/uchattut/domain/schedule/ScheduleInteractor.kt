package com.tla.uchattut.domain.schedule

import com.tla.uchattut.data.db.model.EventDbModel
import com.tla.uchattut.data.repositories.events.EventsRepositoryImpl
import javax.inject.Inject

class ScheduleInteractor @Inject constructor(
    private val eventsRepository: EventsRepositoryImpl
) {
    suspend fun addEvent(eventDbModel: EventDbModel) {
        eventsRepository.addEvent(eventDbModel)
    }

    suspend fun removeEvent(eventDbModel: EventDbModel) {
        eventsRepository.removeEvent(eventDbModel)
    }

    suspend fun getEvents(): List<EventDbModel> {
        return eventsRepository.getEvents()
    }

    suspend fun getEvents(date: String): List<EventDbModel> {
        return eventsRepository.getEvents(date)
    }
}