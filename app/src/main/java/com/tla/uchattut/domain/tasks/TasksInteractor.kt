package com.tla.uchattut.domain.tasks

import com.tla.uchattut.data.db.model.TaskDbModel
import com.tla.uchattut.data.repositories.tasks.TasksRepositoryImpl
import javax.inject.Inject

class TasksInteractor @Inject constructor(
    private val tasksRepository: TasksRepositoryImpl
) {
    suspend fun addTask(task: TaskDbModel) {
        tasksRepository.insertTask(task)
    }

    suspend fun getTasks(): List<TaskDbModel> {
        return tasksRepository.getTasks()
    }

    suspend fun updateTask(task: TaskDbModel) {
        tasksRepository.updateTask(task)
    }

    suspend fun removeTask(task: TaskDbModel) {
        tasksRepository.removeTask(task)
    }
}