package com.tla.uchattut.presentation.tasks.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tla.uchattut.data.db.model.TaskDbModel
import com.tla.uchattut.di.tasks.TasksComponent
import com.tla.uchattut.domain.tasks.TasksInteractor
import com.tla.uchattut.presentation._common.ScopeViewModel
import com.tla.uchattut.presentation._common.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TasksViewModel @Inject constructor(
    private val tasksInteractor: TasksInteractor
) : ScopeViewModel(TasksComponent::class) {

    private val tasksLiveData = MutableLiveData<List<TaskDbModel>>()
    private val putDownTasksEvent = SingleLiveEvent<Unit>()

    fun addTask(task: TaskDbModel) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            tasksInteractor.addTask(task)
            loadTasks()
        }
        putDownTasksEvent.call()
    }

    fun loadTasks() = viewModelScope.launch(Dispatchers.IO) {
        val tasks = tasksInteractor.getTasks()
        withContext(Dispatchers.Main) {
            tasksLiveData.value = tasks
        }
    }

    fun getTasksLiveData(): LiveData<List<TaskDbModel>> {
        return tasksLiveData
    }

    fun getPutDownTaskEvent(): LiveData<Unit?> {
        return putDownTasksEvent
    }

    fun switchCompletionTask(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        val task = tasksLiveData.value?.firstOrNull { it.id == id } ?: return@launch

        task.isCompleted = !task.isCompleted
        tasksInteractor.updateTask(task)
        loadTasks()
    }

    fun removeTask(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        val task = tasksLiveData.value?.firstOrNull { it.id == id } ?: return@launch

        tasksInteractor.removeTask(task)
        loadTasks()
    }

    fun updateTask(id: Int, newContent: String) = viewModelScope.launch(Dispatchers.IO) {
        val task = tasksLiveData.value?.firstOrNull { it.id == id } ?: return@launch

        task.content = newContent

        tasksInteractor.updateTask(task)
        loadTasks()
    }
}