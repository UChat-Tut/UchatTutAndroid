package com.tla.uchattut.di.app

import android.content.Context
import com.google.gson.GsonBuilder
import com.tla.uchattut.presentation._common.resources.AndroidResourceManager
import com.tla.uchattut.presentation._common.resources.ResourceManager
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module(includes = [AppModule.AppAbstractModule::class])
class AppModule(
    private val context: Context?
) {

    @Provides
    fun provideContext(): Context? =
        context

    @Provides
    @AppScope
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://uchattut.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()

    @Module
    interface AppAbstractModule {
        @Binds
        fun bindResourceManager(resourceManager: AndroidResourceManager): ResourceManager
    }
}