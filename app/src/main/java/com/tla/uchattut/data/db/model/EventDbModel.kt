package com.tla.uchattut.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tla.uchattut.data.db.model.EventDbModel.Companion.TABLE_NAME


@Entity(tableName = TABLE_NAME)
data class EventDbModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_NAME_ID)
    var id: Int = 0,

    @ColumnInfo(name = COLUMN_NAME_TITLE)
    val title: String,

    @ColumnInfo(name = COLUMN_NAME_DATE)
    val date: String,

    @ColumnInfo(name = COLUMN_NAME_START_TIMESTAMP)
    val startTimestamp: Long,

    @ColumnInfo(name = COLUMN_NAME_END_TIMESTAMP)
    val endTimestamp: Long
) {
    companion object {
        const val TABLE_NAME = "Event"

        const val COLUMN_NAME_ID = "id"
        const val COLUMN_NAME_TITLE = "title"
        const val COLUMN_NAME_DATE = "date"
        const val COLUMN_NAME_START_TIMESTAMP = "startTimestamp"
        const val COLUMN_NAME_END_TIMESTAMP = "endTimestamp"
    }
}