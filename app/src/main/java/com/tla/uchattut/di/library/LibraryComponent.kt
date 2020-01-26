package com.tla.uchattut.di.library

import com.tla.uchattut.di.DaggerComponent
import com.tla.uchattut.di.app.AppComponent
import com.tla.uchattut.presentation.library.LibraryFragment
import dagger.Component

@LibraryScope
@Component(modules = [LibraryModule::class], dependencies = [AppComponent::class])
interface LibraryComponent : DaggerComponent {
    fun inject(libraryFragment: LibraryFragment)
}