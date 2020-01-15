package com.tla.uchattut.di.tasks

import com.tla.uchattut.domain.tasks.TasksInteractor
import com.tla.uchattut.presentation._common.viewModel
import com.tla.uchattut.presentation.tasks.view.TasksFragment
import com.tla.uchattut.presentation.tasks.view_model.TasksViewModel
import dagger.Module
import dagger.Provides

@Module(includes = [TasksModule.TasksAbstractModule::class])
class TasksModule(
    private val fragment: TasksFragment
) {

    @Provides
    fun provideViewModel(tasksInteractor: TasksInteractor): TasksViewModel =
        fragment.viewModel { TasksViewModel(tasksInteractor) }

    @Module
    interface TasksAbstractModule
}