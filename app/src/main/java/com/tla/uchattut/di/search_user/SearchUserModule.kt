package com.tla.uchattut.di.search_user

import androidx.fragment.app.Fragment
import com.tla.uchattut.domain.search.SearchInteractor
import com.tla.uchattut.presentation._common.viewModel
import com.tla.uchattut.presentation.conversation.search_user.view_model.SearchUserViewModel
import dagger.Module
import dagger.Provides

@Module(includes = [SearchUserModule.SearchUserAbstractModule::class])
class SearchUserModule(
    private val fragment: Fragment
) {
    @Provides
    fun provideViewModel(searchInteractor: SearchInteractor): SearchUserViewModel =
        fragment.viewModel { SearchUserViewModel(searchInteractor) }

    @Module
    interface SearchUserAbstractModule
}