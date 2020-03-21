package com.tla.uchattut.di.search_contacted_user

import androidx.lifecycle.ViewModelProvider
import com.tla.uchattut.domain.search_contacted_users.SearchContactedUsersInteractor
import com.tla.uchattut.presentation._common.factory
import com.tla.uchattut.presentation.schedule.SearchContactedUserViewModel
import dagger.Module
import dagger.Provides

@Module
class SearchContactedUserModule {
    @Provides
    @SearchContactedUserScope
    fun provideViewModelFactory(searchInteractor: SearchContactedUsersInteractor): ViewModelProvider.Factory =
        factory { SearchContactedUserViewModel(searchInteractor) }
}