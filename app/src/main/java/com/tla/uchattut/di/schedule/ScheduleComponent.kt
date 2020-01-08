package com.tla.uchattut.di.schedule

import com.tla.uchattut.di.DaggerComponent
import com.tla.uchattut.di.app.AppComponent
import com.tla.uchattut.presentation.schedule.view.ScheduleFragment
import dagger.Component

@ScheduleScope
@Component(modules = [ScheduleModule::class], dependencies = [AppComponent::class])
interface ScheduleComponent : DaggerComponent {
    fun inject(scheduleFragment: ScheduleFragment)
}