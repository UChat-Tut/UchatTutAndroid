package com.tla.uchattut.di.app

import android.content.Context
import com.google.gson.GsonBuilder
import com.tla.uchattut.data.network.RestApi
import com.tla.uchattut.data.network.RestAuthApi
import com.tla.uchattut.data.network.TokenInterceptor
import com.tla.uchattut.presentation._common.resources.AndroidResourceManager
import com.tla.uchattut.presentation._common.resources.ResourceManager
import dagger.Binds
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
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
    fun provideRetrofit(): Retrofit.Builder =
        Retrofit.Builder()
            .baseUrl("https://uchattut.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))

    @Provides
    @AppScope
    fun provideOkHttpClient(tokenInterceptor: TokenInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(tokenInterceptor)
            .build()

    @Provides
    @AppScope
    fun provideNetworkApi(retrofit: Retrofit.Builder, okHttpClient: OkHttpClient): RestApi =
        retrofit
            .client(okHttpClient)
            .build()
            .create(RestApi::class.java)

    @Provides
    @AppScope
    fun provideNetworkAuthApi(retrofit: Retrofit.Builder): RestAuthApi =
        retrofit
            .build()
            .create(RestAuthApi::class.java)

    @Module
    interface AppAbstractModule {
        @Binds
        fun bindResourceManager(resourceManager: AndroidResourceManager): ResourceManager
    }
}