package com.tla.uchattut.di.auth

import androidx.fragment.app.Fragment
import com.tla.uchattut.domain.auth.AuthInteractor
import com.tla.uchattut.presentation._common.resources.ResourceManager
import com.tla.uchattut.presentation._common.viewModel
import com.tla.uchattut.presentation.auth.sign_in.view_model.SignInViewModel
import com.tla.uchattut.presentation.auth.sign_up.view_model.SignUpViewModel
import dagger.Module
import dagger.Provides

@Module
class AuthModule(
    private val fragment: Fragment?
) {
    @Provides
    fun provideSignInViewModel(
        authInteractor: AuthInteractor,
        resourceManager: ResourceManager
    ): SignInViewModel =
        fragment!!.viewModel { SignInViewModel(authInteractor, resourceManager) }

    @Provides
    fun provideSignUpViewModel(
        authInteractor: AuthInteractor,
        resourceManager: ResourceManager
    ): SignUpViewModel =
        fragment!!.viewModel { SignUpViewModel(authInteractor, resourceManager) }
}