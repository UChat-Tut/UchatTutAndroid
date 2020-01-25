package com.tla.uchattut.di.myprofile

import com.tla.uchattut.di.DaggerComponent
import com.tla.uchattut.di.app.AppComponent
import com.tla.uchattut.presentation.profile.view.MyProfileFragment
import dagger.Component

@MyProfileScope
@Component(modules = [ProfileModule::class], dependencies = [AppComponent::class])
interface MyProfileComponent: DaggerComponent {
    fun inject(myProfileFragment: MyProfileFragment)
}