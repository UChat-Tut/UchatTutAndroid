package com.tla.uchattut.di.auth

import com.tla.uchattut.di.DaggerComponent
import com.tla.uchattut.di.app.AppComponent
import com.tla.uchattut.presentation.auth.AuthActivity
import com.tla.uchattut.presentation.auth.SignInFragment
import com.tla.uchattut.presentation.auth.SignUpFragment
import dagger.Component

@Component(modules = [AuthModule::class], dependencies = [AppComponent::class])
@AuthScope
interface AuthComponent : DaggerComponent {
    fun inject(authActivity: AuthActivity)
    fun inject(signInFragment: SignInFragment)
    fun inject(signUpFragment: SignUpFragment)
}