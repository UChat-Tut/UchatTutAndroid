package com.tla.uchattut.di.schedule

import androidx.lifecycle.ViewModelProvider
import com.tla.uchattut.domain.tasks.TasksInteractor
import com.tla.uchattut.presentation._common.factory
import com.tla.uchattut.presentation._common.resources.ResourceManager
import com.tla.uchattut.presentation._common.viewModel
import com.tla.uchattut.presentation.schedule.ScheduleViewModel
import com.tla.uchattut.presentation.tasks.TasksFragment
import com.tla.uchattut.presentation.tasks.TasksViewModel
import dagger.Module
import dagger.Provides

@Module
class ScheduleModule {
    @Provides
    @ScheduleScope
    fun provideViewModelFactory(resourceManager: ResourceManager): ViewModelProvider.Factory =
        factory { ScheduleViewModel(resourceManager) }
}