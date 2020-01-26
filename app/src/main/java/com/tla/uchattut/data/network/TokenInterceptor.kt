package com.tla.uchattut.data.network

import android.content.Context
import com.tla.uchattut.App
import com.tla.uchattut.data.repositories.auth.AuthRepository
import com.tla.uchattut.presentation._common.openActivity
import com.tla.uchattut.presentation.auth.AuthActivity
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class TokenInterceptor @Inject constructor(): Interceptor {

    companion object {
        private const val TOKEN_HEADER = "Authorization"
    }

    private val authPreserences =
        App.context.getSharedPreferences(AuthRepository.AUTH_PREF_NAME, Context.MODE_PRIVATE)

    private fun getAuthToken(): String? {
        return authPreserences.getString(AuthRepository.TOKEN_PREF_KEY, null)
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        val token = getAuthToken()
        if (token?.isNotBlank() == true) {
            requestBuilder.addHeader(TOKEN_HEADER, "Token $token")
        } else {
            App.context.openActivity(AuthActivity::class.java)
        }
        return chain.proceed(requestBuilder.build())
    }

}