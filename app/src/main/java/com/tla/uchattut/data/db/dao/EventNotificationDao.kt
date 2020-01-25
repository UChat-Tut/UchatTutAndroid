package com.tla.uchattut.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.tla.uchattut.data.db.model.EventNotificationDbModel

@Dao
interface EventNotificationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(notifications: List<EventNotificationDbModel>)
}