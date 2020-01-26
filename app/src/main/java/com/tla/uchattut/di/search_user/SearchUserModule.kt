package com.tla.uchattut.di.search_user

import androidx.lifecycle.ViewModelProvider
import com.tla.uchattut.domain.search.SearchInteractor
import com.tla.uchattut.presentation._common.factory
import com.tla.uchattut.presentation.conversation.search_user.SearchUserViewModel
import dagger.Module
import dagger.Provides

@Module
class SearchUserModule {
    @Provides
    @SearchUserScope
    fun provideViewModelFactory(searchInteractor: SearchInteractor): ViewModelProvider.Factory =
        factory { SearchUserViewModel(searchInteractor) }
}