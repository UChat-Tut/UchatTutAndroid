package com.tla.uchattut.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tla.uchattut.data.db.model.EventNotificationDbModel.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class EventNotificationDbModel(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_NAME_ID)
    val id: Int = 0,

    @ColumnInfo(name = COLUMN_NAME_EVENT_ID)
    val eventId: Int,

    @ColumnInfo(name = COLUMN_NAME_BEFORE_MINUTES)
    val beforeMinutes: Int
) {
    companion object {
        const val TABLE_NAME = "EventNotification"

        const val COLUMN_NAME_ID = "id"
        const val COLUMN_NAME_EVENT_ID = "eventId"
        const val COLUMN_NAME_BEFORE_MINUTES = "beforeMinutes"
    }
}