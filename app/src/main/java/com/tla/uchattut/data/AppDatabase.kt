package com.tla.uchattut.data

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tla.uchattut.App
import com.tla.uchattut.data.db.dao.TaskDao
import com.tla.uchattut.data.db.model.TaskDbModel

@Database(entities = [TaskDbModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract val taskDao: TaskDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    App.context,
                    AppDatabase::class.java,
                    "uchat_tut_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}