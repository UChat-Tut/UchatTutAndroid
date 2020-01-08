package com.tla.uchattut.di.profile

import com.tla.uchattut.di.DaggerComponent
import com.tla.uchattut.di.app.AppComponent
import com.tla.uchattut.presentation.profile.view.ProfileFragment
import dagger.Component

@ProfileScope
@Component(modules = [ProfileModule::class], dependencies = [AppComponent::class])
interface ProfileComponent: DaggerComponent {
    fun inject(profileFragment: ProfileFragment)
}