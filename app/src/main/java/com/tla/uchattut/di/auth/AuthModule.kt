package com.tla.uchattut.di.auth

import androidx.lifecycle.ViewModelProvider
import com.tla.uchattut.domain.auth.AuthInteractor
import com.tla.uchattut.presentation._common.factory
import com.tla.uchattut.presentation._common.resources.ResourceManager
import com.tla.uchattut.presentation.auth.AuthViewModel
import dagger.Module
import dagger.Provides

@Module
class AuthModule {

    @Provides
    @AuthScope
    fun provideViewModelFactory(
        authInteractor: AuthInteractor,
        resourceManager: ResourceManager
    ): ViewModelProvider.Factory =
        factory { AuthViewModel(authInteractor, resourceManager) }
}