package com.tla.uchattut.di.auth

import androidx.fragment.app.FragmentActivity
import com.tla.uchattut.domain.auth.AuthInteractor
import com.tla.uchattut.presentation._common.resources.ResourceManager
import com.tla.uchattut.presentation._common.viewModel
import com.tla.uchattut.presentation.auth.view_model.AuthViewModel
import dagger.Module
import dagger.Provides

@Module
class AuthModule(
    private val activity: FragmentActivity?
) {
    @Provides
    fun provideViewModel(
        authInteractor: AuthInteractor,
        resourceManager: ResourceManager
    ): AuthViewModel =
        activity!!.viewModel { AuthViewModel(authInteractor, resourceManager) }
}