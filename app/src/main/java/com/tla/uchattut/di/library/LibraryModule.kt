package com.tla.uchattut.di.library

import androidx.lifecycle.ViewModelProvider
import com.tla.uchattut.presentation._common.factory
import com.tla.uchattut.presentation.library.LibraryViewModel
import dagger.Module
import dagger.Provides

@Module
class LibraryModule {

    @Provides
    @LibraryScope
    fun provideViewModelFactory(): ViewModelProvider.Factory =
        factory { LibraryViewModel() }
}