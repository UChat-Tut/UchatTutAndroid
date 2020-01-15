package com.tla.uchattut.data.repositories.tasks

import com.tla.uchattut.data.AppDatabase
import com.tla.uchattut.data.db.model.TaskDbModel
import com.tla.uchattut.di.tasks.TasksScope
import javax.inject.Inject

@TasksScope
class TasksRepositoryImpl @Inject constructor() {
    private val taskDao = AppDatabase.getDatabase().taskDao

    suspend fun insertTask(task: TaskDbModel) {
        taskDao.insert(task)
    }

    suspend fun getTasks(): List<TaskDbModel> {
        return taskDao.getAll()
    }

    suspend fun updateTask(task: TaskDbModel) {
        taskDao.update(task)
    }

    suspend fun removeTask(task: TaskDbModel) {
        taskDao.delete(task)
    }
}