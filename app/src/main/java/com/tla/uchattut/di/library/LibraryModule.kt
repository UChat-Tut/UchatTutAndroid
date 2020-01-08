package com.tla.uchattut.di.library

import com.tla.uchattut.presentation._common.viewModel
import com.tla.uchattut.presentation.library.view.LibraryFragment
import com.tla.uchattut.presentation.library.view_model.LibraryViewModel
import dagger.Module

@Module(includes = [LibraryModule.LibraryAbstractModule::class])
class LibraryModule(
    private val fragment: LibraryFragment
) {

    fun provideViewModel(): LibraryViewModel =
        fragment.viewModel { LibraryViewModel() }

    @Module
    interface LibraryAbstractModule
}