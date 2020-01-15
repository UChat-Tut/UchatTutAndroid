package com.tla.uchattut.data.db.dao

import androidx.room.*
import com.tla.uchattut.data.db.model.TaskDbModel

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: TaskDbModel)

    @Update
    suspend fun update(task: TaskDbModel)

    @Delete
    suspend fun delete(task: TaskDbModel)

    @Query("SELECT * FROM ${TaskDbModel.TABLE_NAME}")
    suspend fun getAll(): List<TaskDbModel>
}