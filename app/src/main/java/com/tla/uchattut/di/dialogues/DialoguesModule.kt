package com.tla.uchattut.di.dialogues

import com.tla.uchattut.data.repositories.dialogues.DialoguesRepositoryImpl
import com.tla.uchattut.domain.dialogues.DialoguesInteractor
import com.tla.uchattut.domain.dialogues.DialoguesRepository
import com.tla.uchattut.domain.search.SearchInteractor
import com.tla.uchattut.presentation._common.viewModel
import com.tla.uchattut.presentation.conversation.dialogues.view.DialoguesFragment
import com.tla.uchattut.presentation.conversation.dialogues.view_model.DialoguesViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(includes = [DialoguesModule.DialoguesAbstractModule::class])
class DialoguesModule(
    private val fragment: DialoguesFragment
) {
    @Provides
    fun provideViewModel(
        dialoguesInteractor: DialoguesInteractor,
        searchInteractor: SearchInteractor
    ): DialoguesViewModel =
        fragment.viewModel { DialoguesViewModel(dialoguesInteractor, searchInteractor) }

    @Module
    interface DialoguesAbstractModule {
        @Binds
        @DialoguesScope
        fun bindDialoguesRepository(repository: DialoguesRepositoryImpl): DialoguesRepository
    }
}