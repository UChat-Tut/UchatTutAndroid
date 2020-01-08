package com.tla.uchattut.di.app

import com.tla.uchattut.di.DaggerComponent
import com.tla.uchattut.presentation._common.resources.ResourceManager
import dagger.Component
import retrofit2.Retrofit

@AppScope
@Component(modules = [AppModule::class])
interface AppComponent : DaggerComponent {
    fun resourceManager(): ResourceManager
    fun retrofit(): Retrofit
}