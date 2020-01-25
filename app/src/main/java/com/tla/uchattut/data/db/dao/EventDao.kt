package com.tla.uchattut.data.db.dao

import androidx.room.*
import com.tla.uchattut.data.db.model.EventDbModel

@Dao
interface EventDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(eventDbModel: EventDbModel)

    @Delete
    suspend fun delete(eventDbModel: EventDbModel)

    @Query("SELECT * FROM ${EventDbModel.TABLE_NAME} WHERE ${EventDbModel.COLUMN_NAME_DATE} >= :start AND ${EventDbModel.COLUMN_NAME_DATE} <= :end")
    suspend fun getInPeriod(start: String, end: String): List<EventDbModel>

    @Query("SELECT * FROM ${EventDbModel.TABLE_NAME}")
    suspend fun getAll(): List<EventDbModel>

    @Query("SELECT * FROM ${EventDbModel.TABLE_NAME} WHERE ${EventDbModel.COLUMN_NAME_DATE} = :date")
    suspend fun getByDate(date: String): List<EventDbModel>

}