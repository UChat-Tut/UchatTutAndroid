package com.tla.uchattut.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tla.uchattut.data.db.model.TaskDbModel.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class TaskDbModel(
    @ColumnInfo(name = COLUMN_NAME_CONTENT)
    var content: String,

    @ColumnInfo(name = COLUMN_NAME_COMPLETED)
    var isCompleted: Boolean = false
) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_NAME_ID)
    var id: Int = 0

    companion object {
        const val TABLE_NAME = "Task"

        const val COLUMN_NAME_ID = "id"
        const val COLUMN_NAME_CONTENT = "content"
        const val COLUMN_NAME_COMPLETED = "isCompleted"
    }
}