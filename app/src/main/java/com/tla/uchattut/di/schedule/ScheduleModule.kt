package com.tla.uchattut.di.schedule

import com.tla.uchattut.domain.schedule.ScheduleInteractor
import com.tla.uchattut.presentation._common.viewModel
import com.tla.uchattut.presentation.schedule.view.ScheduleFragment
import com.tla.uchattut.presentation.schedule.view_model.ScheduleViewModel
import dagger.Module
import dagger.Provides

@Module(includes = [ScheduleModule.ScheduleAbstractModule::class])
class ScheduleModule(
    private val fragment: ScheduleFragment
) {

    @Provides
    fun provideViewModel(scheduleInteractor: ScheduleInteractor): ScheduleViewModel =
        fragment.viewModel { ScheduleViewModel(scheduleInteractor) }

    @Module
    interface ScheduleAbstractModule
}