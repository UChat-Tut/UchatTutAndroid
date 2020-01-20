package com.tla.uchattut.data.repositories.events

import com.tla.uchattut.data.db.AppDatabase
import com.tla.uchattut.data.db.model.EventDbModel
import com.tla.uchattut.domain._common.CalendarWrapper
import com.tla.uchattut.presentation.schedule.model.EventPresentationModel
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class EventsRepositoryImpl @Inject constructor() {

    private val eventDao = AppDatabase.getDatabase().eventDao
    private val eventNotificationDao = AppDatabase.getDatabase().eventNotificationDao

    suspend fun addEvent(eventModel: EventPresentationModel) {
        eventDao.insert(eventModel.convertToDbModel())
    }

    suspend fun removeEvent(eventDbModel: EventDbModel) {
        eventDao.delete(eventDbModel)
    }

    suspend fun getEvents(): List<EventDbModel> {
        return eventDao.getAll()
    }

    suspend fun getEvents(date: Date): List<EventPresentationModel> {
        val time = dateToString(date)
        return eventDao.getByDate(time).map {
            it.convertToDbModel()
        }
    }

    fun dateToString(date: Date): String {
        val format = SimpleDateFormat("yyyy.MM.dd")
        return format.format(date)
    }

    private fun stringToDate(time: String): Date {
        val format = SimpleDateFormat("yyyy.MM.dd")
        return format.parse(time)!!
    }

    suspend fun getEventsInPeriod(start: Date, end: Date): List<EventPresentationModel> {
        val startFormattedDate = dateToString(start)
        val endFormattedDate = dateToString(end)
        return eventDao.getInPeriod(startFormattedDate, endFormattedDate).map {
            it.convertToDbModel()
        }
    }

    private fun EventPresentationModel.convertToDbModel(): EventDbModel = EventDbModel(
        id = id,
        title = title,
        date = dateToString(date),
        startTimestamp = startCalendarTime.time.time,
        endTimestamp = endCalendarTime.time.time,
        color = color
    )

    private fun EventDbModel.convertToDbModel(): EventPresentationModel = EventPresentationModel(
        id = id,
        title = title,
        date = stringToDate(date),
        startCalendarTime = CalendarWrapper.getDefaultInstance().apply {
            timeInMillis = startTimestamp
        },
        endCalendarTime = CalendarWrapper.getDefaultInstance().apply {
            timeInMillis = endTimestamp
        },
        color = color
    )
}