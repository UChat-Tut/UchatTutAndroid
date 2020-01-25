package com.tla.uchattut.di.auth

import com.tla.uchattut.di.DaggerComponent
import com.tla.uchattut.di.app.AppModule
import com.tla.uchattut.presentation.auth.view.AuthActivity
import com.tla.uchattut.presentation.auth.view.SignInFragment
import com.tla.uchattut.presentation.auth.view.SignUpFragment
import com.tla.uchattut.presentation.main.MainActivity
import dagger.Component

@Component(modules = [AuthModule::class, AppModule::class])
@AuthScope
interface AuthComponent : DaggerComponent {
    fun inject(authActivity: AuthActivity)
    fun inject(signInFragment: SignInFragment)
    fun inject(signUpFragment: SignUpFragment)
}