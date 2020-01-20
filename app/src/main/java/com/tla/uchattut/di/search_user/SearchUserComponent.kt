package com.tla.uchattut.di.search_user

import com.tla.uchattut.di.DaggerComponent
import com.tla.uchattut.di.app.AppComponent
import com.tla.uchattut.presentation.conversation.search_user.view.SearchUserFragment
import dagger.Component

@SearchUserScope
@Component(modules = [SearchUserModule::class], dependencies = [AppComponent::class])
interface SearchUserComponent : DaggerComponent {
    fun inject(searchUserFragment: SearchUserFragment)
}