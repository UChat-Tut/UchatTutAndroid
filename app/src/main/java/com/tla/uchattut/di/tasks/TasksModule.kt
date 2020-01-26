package com.tla.uchattut.di.tasks

import androidx.lifecycle.ViewModelProvider
import com.tla.uchattut.domain.tasks.TasksInteractor
import com.tla.uchattut.presentation._common.factory
import com.tla.uchattut.presentation.tasks.TasksViewModel
import dagger.Module
import dagger.Provides

@Module
class TasksModule {
    @Provides
    @TasksScope
    fun provideViewModel(tasksInteractor: TasksInteractor): ViewModelProvider.Factory =
        factory { TasksViewModel(tasksInteractor) }
}