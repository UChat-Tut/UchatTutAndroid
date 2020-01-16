package com.tla.uchattut.data.repositories.events

import com.tla.uchattut.data.db.AppDatabase
import com.tla.uchattut.data.db.model.EventDbModel
import com.tla.uchattut.di.schedule.ScheduleScope
import javax.inject.Inject

@ScheduleScope
class EventsRepositoryImpl @Inject constructor() {

    private val eventDao = AppDatabase.getDatabase().eventDao

    suspend fun addEvent(eventDbModel: EventDbModel) {
        eventDao.insert(eventDbModel)
    }

    suspend fun removeEvent(eventDbModel: EventDbModel) {
        eventDao.delete(eventDbModel)
    }

    suspend fun getEvents(): List<EventDbModel> {
        return eventDao.getAll()
    }

    suspend fun getEvents(date: String): List<EventDbModel> {
        return eventDao.getByDate(date)
    }
}