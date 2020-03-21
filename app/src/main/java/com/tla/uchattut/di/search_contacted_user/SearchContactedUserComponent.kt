package com.tla.uchattut.di.search_contacted_user

import com.tla.uchattut.di.DaggerComponent
import com.tla.uchattut.di.app.AppComponent
import com.tla.uchattut.presentation.schedule.SearchContactedUserFragment
import dagger.Component

@SearchContactedUserScope
@Component(modules = [SearchContactedUserModule::class], dependencies = [AppComponent::class])
interface SearchContactedUserComponent : DaggerComponent {
    fun inject(fragment: SearchContactedUserFragment)
}