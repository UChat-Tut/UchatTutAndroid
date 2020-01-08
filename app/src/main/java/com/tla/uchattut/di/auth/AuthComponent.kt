package com.tla.uchattut.di.auth

import com.tla.uchattut.data.repositories.auth.AuthRepository
import com.tla.uchattut.di.DaggerComponent
import com.tla.uchattut.di.app.AppComponent
import com.tla.uchattut.presentation.auth.sign_in.view.SignInFragment
import com.tla.uchattut.presentation.auth.sign_up.view.SignUpFragment
import com.tla.uchattut.presentation.main.MainActivity
import dagger.Component

@AuthScope
@Component(modules = [AuthModule::class], dependencies = [AppComponent::class])
interface AuthComponent : DaggerComponent {

    fun authRepository(): AuthRepository

    fun inject(mainActivity: MainActivity)
    fun inject(signInFragment: SignInFragment)
    fun inject(signUpFragment: SignUpFragment)
}