package com.tla.uchattut.di.tasks

import com.tla.uchattut.di.DaggerComponent
import com.tla.uchattut.di.app.AppComponent
import com.tla.uchattut.presentation.tasks.TasksFragment
import dagger.Component

@TasksScope
@Component(modules = [TasksModule::class], dependencies = [AppComponent::class])
interface TasksComponent : DaggerComponent {
    fun inject(tasksFragment: TasksFragment)
}