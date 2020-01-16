package com.tla.uchattut.data.db.dao

import androidx.room.*
import com.tla.uchattut.data.db.model.EventDbModel

@Dao
interface EventDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(eventDbModel: EventDbModel)

    @Delete
    suspend fun delete(eventDbModel: EventDbModel)

    @Query("SELECT * FROM ${EventDbModel.TABLE_NAME}")
    suspend fun getAll(): List<EventDbModel>

    @Query("SELECT * FROM ${EventDbModel.TABLE_NAME} WHERE date = :date")
    suspend fun getByDate(date: String): List<EventDbModel>

}