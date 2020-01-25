package com.tla.uchattut.data.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tla.uchattut.App
import com.tla.uchattut.data.db.dao.EventDao
import com.tla.uchattut.data.db.dao.EventNotificationDao
import com.tla.uchattut.data.db.dao.TaskDao
import com.tla.uchattut.data.db.model.EventDbModel
import com.tla.uchattut.data.db.model.EventNotificationDbModel
import com.tla.uchattut.data.db.model.TaskDbModel

@Database(entities = [TaskDbModel::class, EventDbModel::class, EventNotificationDbModel::class], version = 7)
abstract class AppDatabase : RoomDatabase() {

    abstract val taskDao: TaskDao
    abstract val eventDao: EventDao
    abstract val eventNotificationDao: EventNotificationDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(): AppDatabase {
            val tempInstance =
                INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    App.context,
                    AppDatabase::class.java,
                    "uchat_tut_database"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}