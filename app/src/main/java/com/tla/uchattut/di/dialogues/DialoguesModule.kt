package com.tla.uchattut.di.dialogues

import androidx.lifecycle.ViewModelProvider
import com.tla.uchattut.data.repositories.dialogues.DialoguesRepositoryImpl
import com.tla.uchattut.domain.dialogues.DialoguesInteractor
import com.tla.uchattut.domain.dialogues.DialoguesRepository
import com.tla.uchattut.domain.search.SearchInteractor
import com.tla.uchattut.presentation._common.factory
import com.tla.uchattut.presentation.conversation.dialogues.DialoguesViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(includes = [DialoguesModule.DialoguesAbstractModule::class])
class DialoguesModule {

    @Provides
    @DialoguesScope
    fun provideViewModel(
        dialoguesInteractor: DialoguesInteractor,
        searchInteractor: SearchInteractor
    ): ViewModelProvider.Factory =
        factory { DialoguesViewModel(dialoguesInteractor, searchInteractor) }

    @Module
    interface DialoguesAbstractModule {
        @Binds
        fun bindDialoguesRepository(repository: DialoguesRepositoryImpl): DialoguesRepository
    }
}